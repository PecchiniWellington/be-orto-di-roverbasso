package com.ortoroverbasso.ortorovebasso.service.cart.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartItemEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.mapper.cart.CartMapper;
import com.ortoroverbasso.ortorovebasso.repository.cart.CartItemRepository;
import com.ortoroverbasso.ortorovebasso.repository.cart.CartRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartResponseDto getCart(String cartToken) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Usa il mapper per mappare l'entità CartEntity nel DTO CartResponseDto
        return CartMapper.mapToCartResponseDto(cart);
    }

    @Override
    public CartResponseDto addItemToCart(String cartToken, CartRequestDto cartRequestDto) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        ProductEntity product = productRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartRequestDto.getQuantity());

        cartItemRepository.save(cartItem);

        // Usa il mapper per restituire il DTO
        return CartMapper.mapToCartResponseDto(cart);
    }

    @Override
    public CartResponseDto mergeCarts(String cartToken, Long userId) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartEntity userCart = cartRepository.findByUserId(userId).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            newCart.setUser(new UserEntity(userId)); // Assicurati che la classe User sia correttamente gestita
            return newCart;
        });

        // Merge items
        for (CartItemEntity cartItem : cart.getItems()) {
            Optional<CartItemEntity> existingItem = userCart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(cartItem.getProduct().getId()))
                    .findFirst();

            if (existingItem.isPresent()) {
                existingItem.get().setQuantity(existingItem.get().getQuantity() + cartItem.getQuantity());
            } else {
                CartItemEntity newCartItem = new CartItemEntity();
                newCartItem.setCart(userCart);
                newCartItem.setProduct(cartItem.getProduct());
                newCartItem.setQuantity(cartItem.getQuantity());
                userCart.getItems().add(newCartItem);
            }
        }

        cartRepository.save(userCart);

        // Usa il mapper per restituire il DTO
        return CartMapper.mapToCartResponseDto(userCart);
    }
}
