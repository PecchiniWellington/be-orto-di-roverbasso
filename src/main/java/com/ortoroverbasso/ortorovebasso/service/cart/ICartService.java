package com.ortoroverbasso.ortorovebasso.service.cart;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;

public interface ICartService {
    CartResponseDto getCart(String cartToken);

    CartResponseDto getCart(Long userId);

    CartResponseDto addItemToCart(String cartToken, CartRequestDto cartRequestDto);

    CartResponseDto addItemToCart(Long userId, CartRequestDto cartRequestDto);

    CartResponseDto removeItemFromCart(String cartToken, Long productId, int quantity);

    CartResponseDto removeItemFromCart(Long userId, Long productId, int quantity);

    CartResponseDto mergeCarts(Long userId, String cartToken);

    CartResponseDto clearCart(Long userId);

    CartResponseDto clearCart(String cartToken);

    String createCart();

    CartEntity getCartEntityByToken(String cartToken);

    CartResponseDto getCartById(Long cartId);

    String createCart(Long userId);

    String createCart(String cartToken);

    void createCartWithToken(String cartToken);

}
