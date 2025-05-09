package com.ortoroverbasso.ortorovebasso.dto.cart;

import java.util.List;

public class CartResponseDto {
    private Long cartId;
    private String cartToken;
    private List<CartItemDto> items;
    private Long orderCustomId; // Add this field to store the OrderCustom ID

    // Aggiungi questi campi
    private int totalQuantity;
    private String totalPrice;

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

    // Getter e setter per totalQuantity e totalPrice
    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Getter and setter for orderCustomId
    public Long getOrderCustomId() {
        return orderCustomId;
    }

    public void setOrderCustomId(Long orderCustomId) {
        this.orderCustomId = orderCustomId;
    }
}
