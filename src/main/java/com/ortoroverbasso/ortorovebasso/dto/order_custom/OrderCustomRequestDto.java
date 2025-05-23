package com.ortoroverbasso.ortorovebasso.dto.order_custom;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;
import com.ortoroverbasso.ortorovebasso.enums.StatusOrderEnum;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderCustomRequestDto {

    @NotEmpty(message = "Products cannot be empty")
    private List<Long> productIds;

    @NotNull(message = "Pickup information is required")
    private PickupEntity pickupOrder; // ← usa direttamente l'entità salvata

    StatusOrderEnum statusOrder;

    // Getter e Setter
    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public PickupEntity getPickupOrder() {
        return pickupOrder;
    }

    public void setPickupOrder(PickupEntity pickupOrder) {
        this.pickupOrder = pickupOrder;
    }

    public StatusOrderEnum getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrderEnum statusOrder) {
        this.statusOrder = statusOrder;
    }
}
