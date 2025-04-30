package com.ortoroverbasso.ortorovebasso.dto.cart;

import java.util.List;

public class CartResponseDto {
    private Long cartId;
    private String cartToken;
    private List<CartItemDto> items;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getCartToken() {
        return cartToken;
    }

    public void setCartToken(String cartToken) {
        this.cartToken = cartToken;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

}
