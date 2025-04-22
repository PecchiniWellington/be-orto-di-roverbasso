package com.ortoroverbasso.ortorovebasso.dto.manufacturer;

import java.util.List;

public class ManufacturerProductAssociationRequestDto {

    private List<Long> productIds;

    public ManufacturerProductAssociationRequestDto() {
    }

    // Parametrized constructor
    public ManufacturerProductAssociationRequestDto(List<Long> productIds) {
        this.productIds = productIds;
    }

    // Getter and Setter
    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
