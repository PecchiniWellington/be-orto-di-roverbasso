package com.ortoroverbasso.ortorovebasso.dto.order_custom;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupResponseDto;
import com.ortoroverbasso.ortorovebasso.enums.StatusOrderEnum;

public class OrderCustomResponseDto {

    private Long id;
    private String token;
    StatusOrderEnum statusOrder;
    private Long cartId;

    private PickupResponseDto pickupOrder;

    private List<OrderCustomProductDto> products;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public StatusOrderEnum getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrderEnum statusOrder) {
        this.statusOrder = statusOrder;
    }

    public PickupResponseDto getPickupOrder() {
        return pickupOrder;
    }

    public void setPickupOrder(PickupResponseDto pickupOrder) {
        this.pickupOrder = pickupOrder;
    }

    public List<OrderCustomProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<OrderCustomProductDto> products) {
        this.products = products;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
}
