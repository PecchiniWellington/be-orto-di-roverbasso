package com.ortoroverbasso.ortorovebasso.mapper.cart;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.cart.CartItemDto;
import com.ortoroverbasso.ortorovebasso.dto.cart.CartResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartItemEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

public class CartMapper {

    // Mappa da CartItemEntity a CartItemDto
    public static CartItemDto mapToCartItemDto(CartItemEntity cartItemEntity) {
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

        // Add the OrderCustom ID if there are any orders
        if (cartEntity.getOrders() != null && !cartEntity.getOrders().isEmpty()) {
            // Get the ID of the first order (you might want to change this logic depending
            // on requirements)
            cartResponseDto.setOrderCustomId(cartEntity.getOrders().get(0).getId());
        }

        return cartResponseDto;
    }

    // ✅ NUOVO: Mappa da CartItemDto a CartItemEntity
    public static CartItemEntity mapToCartItemEntity(CartItemDto dto) {
        CartItemEntity entity = new CartItemEntity();
        entity.setQuantity(dto.getQuantity());

        // Costruiamo un ProductEntity con solo l'ID (verrà gestito come riferimento)
        ProductEntity product = new ProductEntity();
        product.setId(dto.getProductId());
        entity.setProduct(product);

        return entity;
    }

    // ✅ Opzionale: lista di CartItemDto ➝ CartItemEntity
    public static List<CartItemEntity> mapToCartItemEntityList(List<CartItemDto> dtos) {
        return dtos.stream()
                .map(CartMapper::mapToCartItemEntity)
                .collect(Collectors.toList());
    }
}
