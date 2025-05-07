package com.ortoroverbasso.ortorovebasso.mapper.cart;

import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartItemDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartItemEntity;

public class CartMapper {

    // Mappa una lista di CartItemEntity in una lista di CartItemDto
    public static CartItemDto mapToCartItemDto(CartItemEntity cartItemEntity) {
        // Ora otteniamo il nome dal ProductInformationEntity
        String productName = cartItemEntity.getProduct().getProductInformation().getName();
        Double productPrice = cartItemEntity.getProduct().getRetailPrice();

        return new CartItemDto(
                cartItemEntity.getProduct().getId(),
                productName,
                cartItemEntity.getQuantity(),
                productPrice);
    }

    // Mappa CartEntity in CartResponseDto
    public static CartResponseDto mapToCartResponseDto(CartEntity cartEntity) {
        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartId(cartEntity.getId());
        cartResponseDto.setCartToken(cartEntity.getCartToken());
        cartResponseDto.setItems(cartEntity.getItems().stream()
                .map(CartMapper::mapToCartItemDto)
                .collect(Collectors.toList()));
        return cartResponseDto;
    }
}
