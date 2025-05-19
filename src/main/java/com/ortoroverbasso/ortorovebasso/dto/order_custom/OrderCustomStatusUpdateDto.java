package com.ortoroverbasso.ortorovebasso.dto.order_custom;

import com.ortoroverbasso.ortorovebasso.enums.StatusOrderEnum;

import jakarta.validation.constraints.NotBlank;

public class OrderCustomStatusUpdateDto {

    @NotBlank(message = "Status order cannot be blank")
    private StatusOrderEnum statusOrder;

    public StatusOrderEnum getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrderEnum statusOrder) {
        this.statusOrder = statusOrder;
    }
}
