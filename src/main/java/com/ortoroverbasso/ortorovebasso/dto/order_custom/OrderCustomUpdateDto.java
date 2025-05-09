package com.ortoroverbasso.ortorovebasso.dto.order_custom;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.pickup.PickupRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductQuantityDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class OrderCustomUpdateDto {

    @Valid
    private PickupRequestDto pickupOrder;

    @NotEmpty(message = "Products cannot be empty")
    private List<ProductQuantityDto> products;

    // Getters e Setters
    public PickupRequestDto getPickupOrder() {
        return pickupOrder;
    }

    public void setPickupOrder(PickupRequestDto pickupOrder) {
        this.pickupOrder = pickupOrder;
    }

    public List<ProductQuantityDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductQuantityDto> products) {
        this.products = products;
    }
}
