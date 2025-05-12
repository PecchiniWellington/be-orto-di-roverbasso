package com.ortoroverbasso.ortorovebasso.service.cart.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public String createCart() {
        CartEntity cart = new CartEntity();
        cart.setCartToken(UUID.randomUUID().toString());
        cartRepository.save(cart);
        return cart.getCartToken();
    }

    @Override
    public String createCart(Long userId) {
        CartEntity cart = new CartEntity();
        cart.setCartToken(UUID.randomUUID().toString());
        cart.setUser(new UserEntity(userId));
        cartRepository.save(cart);
        return cart.getCartToken();
    }

    @Override
    public String createCart(String cartToken) {
        CartEntity cart = new CartEntity();
        cart.setCartToken(cartToken);
        cartRepository.save(cart);
        return cart.getCartToken();
    }

    private void calculateCartTotals(List<CartItemDto> cartItems) {
        cartItems.forEach(item -> {
        });
    }

    private CartResponseDto getCartInternal(CartEntity cart) {
        List<CartItemDto> cartItems = cart.getItems().stream()
                .sorted(Comparator.comparing(CartItemEntity::getAddedAt,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .map(this::createCartItemDto)
                .collect(Collectors.toList());

        calculateCartTotals(cartItems);

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cart.getId());
        cartResponseDto.setCartToken(cart.getCartToken());
        cartResponseDto.setItems(cartItems);

        if (cart.getOrders() != null && !cart.getOrders().isEmpty()) {
            cartResponseDto.setOrderCustomId(cart.getOrders().get(0).getId());
        }

        int totalQuantity = cartItems.stream().mapToInt(CartItemDto::getQuantity).sum();
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        cartResponseDto.setTotalQuantity(totalQuantity);
        cartResponseDto.setTotalPrice(String.format("%.2f", totalPrice));

        return cartResponseDto;
    }

    private CartItemDto createCartItemDto(CartItemEntity item) {
        return new CartItemDto(
                item.getProduct().getId(),
                item.getProduct().getProductInformation() != null ? item.getProduct().getProductInformation().getName()
                        : "Unknown Product",
                item.getQuantity(),
                item.getProduct().getRetailPrice());
    }

    private void deleteCartItem(CartEntity cart, CartItemEntity cartItem) {
        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

    private CartResponseDto removeItemFromCartInternal(CartEntity cart, Long productId, int quantity) {
        CartItemEntity cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (quantity >= cartItem.getQuantity()) {
            deleteCartItem(cart, cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() - quantity);
            cartItemRepository.save(cartItem);
        }

        cartRepository.save(cart);
        return getCartInternal(cart);
    }

    private CartResponseDto addItemToCartInternal(CartEntity cart, CartRequestDto cartRequestDto) {
        ProductEntity product = productRepository.findById(cartRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItemEntity existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequestDto.getQuantity());
            cartItemRepository.save(existingCartItem);
        } else {
            CartItemEntity newCartItem = new CartItemEntity();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(cartRequestDto.getQuantity());
            newCartItem.setAddedAt(LocalDateTime.now());
            cart.getItems().add(newCartItem);
            cartItemRepository.save(newCartItem);
        }

        cartRepository.save(cart);
        return getCartInternal(cart);
    }

    @Transactional
    private CartResponseDto clearCartInternal(CartEntity cart) {
        List<CartItemEntity> itemsToRemove = new ArrayList<>(cart.getItems());
        cart.getItems().clear();

        if (!itemsToRemove.isEmpty()) {
            cartItemRepository.deleteAll(itemsToRemove);
        }

        cartRepository.save(cart);

        CartResponseDto response = new CartResponseDto();
        response.setCartId(cart.getId());
        response.setCartToken(cart.getCartToken());
        response.setItems(new ArrayList<>());
        response.setTotalQuantity(0);
        response.setTotalPrice("0.00");
        return response;
    }

    @Override
    public CartResponseDto addItemToCart(String cartToken, CartRequestDto cartRequestDto) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return addItemToCartInternal(cart, cartRequestDto);
    }

    @Override
    public CartResponseDto addItemToCart(Long userId, CartRequestDto cartRequestDto) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));
        return addItemToCartInternal(cart, cartRequestDto);
    }

    @Override
    public CartResponseDto removeItemFromCart(Long userId, Long productId, int quantity) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));
        return removeItemFromCartInternal(cart, productId, quantity);
    }

    @Override
    public CartResponseDto removeItemFromCart(String cartToken, Long productId, int quantity) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return removeItemFromCartInternal(cart, productId, quantity);
    }

    @Override
    public CartResponseDto clearCart(Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));
        return clearCartInternal(cart);
    }

    @Override
    public CartResponseDto clearCart(String cartToken) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return clearCartInternal(cart);
    }

    @Override
    public CartResponseDto mergeCarts(Long userId, String cartToken) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartEntity userCart = cartRepository.findByUserId(userId).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            newCart.setUser(new UserEntity(userId));
            return newCart;
        });

        cartRepository.save(userCart);
        return CartMapper.mapToCartResponseDto(userCart);
    }

    @Override
    public CartResponseDto getCart(String cartToken) {
        CartEntity cart = cartRepository.findByCartToken(cartToken)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setCartToken(cartToken);
                    return cartRepository.save(newCart);
                });

        return getCartInternal(cart);
    }

    @Override
    public CartResponseDto getCart(Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            newCart.setUser(new UserEntity(userId));
            newCart.setCartToken(UUID.randomUUID().toString());
            return cartRepository.save(newCart);
        });

        return getCartInternal(cart);
    }

    @Override
    public CartEntity getCartEntityByToken(String cartToken) {
        return cartRepository.findByCartToken(cartToken)
                .orElseThrow(() -> new RuntimeException("Cart not found with token: " + cartToken));
    }

    @Override
    public CartResponseDto getCartById(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        return getCartInternal(cart);
    }

    @Override
    public void createCartWithToken(String cartToken) {
        boolean exists = cartRepository.existsByCartToken(cartToken);
        if (exists) {
            System.out.println("[CART SERVICE] Cart esiste già per token: " + cartToken);
            return;
        }

        CartEntity cart = new CartEntity();
        cart.setCartToken(cartToken);
        cartRepository.save(cart);
        System.out.println("[CART SERVICE] Cart creato con token: " + cartToken);
    }

    @Override
    public boolean existsByCartToken(String cartToken) {
        return cartRepository.existsByCartToken(cartToken);
    }
}
