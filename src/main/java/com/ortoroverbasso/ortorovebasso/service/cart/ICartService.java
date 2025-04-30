package com.ortoroverbasso.ortorovebasso.service.cart;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;

public interface ICartService {
    CartResponseDto getCart(String cartToken);

    CartResponseDto addItemToCart(String cartToken, CartRequestDto cartRequestDto);

    CartResponseDto mergeCarts(String cartToken, Long userId);
}
