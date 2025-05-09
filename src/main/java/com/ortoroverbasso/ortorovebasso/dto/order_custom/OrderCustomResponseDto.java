package com.ortoroverbasso.ortorovebasso.dto.order_custom;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;

public class OrderCustomResponseDto {
    private Long id;
    private String token;
    private List<ProductResponseDto> products;
    private PickupResponseDto pickupOrder;
    private String statusOrder;

    // Getters and Setters
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

    public List<ProductResponseDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDto> products) {
        this.products = products;
    }

    public PickupResponseDto getPickupOrder() {
        return pickupOrder;
    }

    public void setPickupOrder(PickupResponseDto pickupOrder) {
        this.pickupOrder = pickupOrder;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }
}
