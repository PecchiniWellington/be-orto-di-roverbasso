package com.ortoroverbasso.ortorovebasso.service.cart;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;

public interface ICartService {
    CartResponseDto getCart(String cartToken);

    CartResponseDto addItemToCart(String cartToken, CartRequestDto cartRequestDto);

    CartResponseDto mergeCarts(Long userId, String cartToken);

    CartResponseDto getCart(Long userId);

    CartResponseDto addItemToCart(Long userId, CartRequestDto cartRequestDto);

    String createCart();
}
