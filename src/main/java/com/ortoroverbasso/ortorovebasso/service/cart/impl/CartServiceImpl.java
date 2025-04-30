package com.ortoroverbasso.ortorovebasso.service.cart.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartItemDto;
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

        // Mappa il carrello e i suoi articoli
        List<CartItemDto> cartItems = cart.getItems().stream()
                .map(item -> {
                    // Gestisci il caso in cui ProductInformation è null
                    String productName = "Unknown Product"; // Nome di fallback
                    if (item.getProduct().getProductInformation() != null) {
                        productName = item.getProduct().getProductInformation().getName();
                    }
                    return new CartItemDto(
                            item.getProduct().getId(),
                            productName, // Usa il nome del prodotto o il nome di fallback
                            item.getQuantity(),
                            item.getProduct().getRetailPrice().toString());
                })
                .collect(Collectors.toList());

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cart.getId());
        cartResponseDto.setCartToken(cart.getCartToken());
        cartResponseDto.setItems(cartItems);

        return cartResponseDto;
    }

    @Override
    public CartResponseDto getCart(Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));

        // Mappa il carrello e i suoi articoli
        List<CartItemDto> cartItems = cart.getItems().stream()
                .map(item -> {
                    String productName = "Unknown Product";
                    if (item.getProduct().getProductInformation() != null) {
                        productName = item.getProduct().getProductInformation().getName();
                    }
                    return new CartItemDto(
                            item.getProduct().getId(),
                            productName,
                            item.getQuantity(),
                            item.getProduct().getRetailPrice().toString());
                })
                .collect(Collectors.toList());

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cart.getId());
        cartResponseDto.setCartToken(cart.getCartToken());
        cartResponseDto.setItems(cartItems);

        return cartResponseDto;
    }

    @Override
    public CartResponseDto addItemToCart(String cartToken, CartRequestDto cartRequestDto) {
        // Recupera il carrello tramite il cartToken
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Recupera il prodotto tramite il productId
        ProductEntity product = productRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Ottieni il nome del prodotto da ProductInformation
        String productName = null;
        if (product.getProductInformation() != null) {
            productName = product.getProductInformation().getName(); // Recupera il nome dal ProductInformation
        }

        // Crea un nuovo cart item
        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartRequestDto.getQuantity());

        // Salva l'articolo nel carrello
        cartItemRepository.save(cartItem);

        // Mappa il carrello e restituisci il DTO
        List<CartItemDto> cartItems = cart.getItems().stream()
                .map(item -> new CartItemDto(item.getProduct().getId(),
                        item.getProduct().getProductInformation() != null
                                ? item.getProduct().getProductInformation().getName()
                                : "Default Product",
                        item.getQuantity(),
                        item.getProduct().getRetailPrice())) // Usando il nome da ProductInformation
                .collect(Collectors.toList());

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cart.getId());
        cartResponseDto.setCartToken(cart.getCartToken());
        cartResponseDto.setItems(cartItems);

        return cartResponseDto;
    }

    @Override
    public CartResponseDto addItemToCart(Long userId, CartRequestDto cartRequestDto) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));

        ProductEntity product = productRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Ottieni il nome del prodotto da ProductInformation
        String productName = null;
        if (product.getProductInformation() != null) {
            productName = product.getProductInformation().getName(); // Recupera il nome dal ProductInformation
        }

        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartRequestDto.getQuantity());

        // Salva il cart item
        cartItemRepository.save(cartItem);

        // Mappa il cart e restituisci il DTO
        List<CartItemDto> cartItems = cart.getItems().stream()
                .map(item -> new CartItemDto(item.getProduct().getId(),
                        item.getProduct().getProductInformation() != null
                                ? item.getProduct().getProductInformation().getName()
                                : "Default Product",
                        item.getQuantity(),
                        item.getProduct().getRetailPrice())) // Usando il nome da ProductInformation
                .collect(Collectors.toList());

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cart.getId());
        cartResponseDto.setCartToken(cart.getCartToken());
        cartResponseDto.setItems(cartItems);

        return cartResponseDto;
    }

    @Override
    public CartResponseDto mergeCarts(Long userId, String cartToken) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartEntity userCart = cartRepository.findByUserId(userId).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            newCart.setUser(new UserEntity(userId)); // Correct handling of user
            return newCart;
        });

        // Merge items logic...

        cartRepository.save(userCart);
        return CartMapper.mapToCartResponseDto(userCart);
    }

    public String createCart() {
        CartEntity cart = new CartEntity();
        cart.setCartToken(UUID.randomUUID().toString());
        cartRepository.save(cart);
        return cart.getCartToken();
    }

}
