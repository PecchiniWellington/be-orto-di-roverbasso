package com.ortoroverbasso.ortorovebasso.dto.product.product_variation;

import java.util.List;

public class VariationRequestDto {
    private Long id;
    private List<Long> attributeIds;

    public VariationRequestDto() {
    }

    public VariationRequestDto(
            Long id,
            List<Long> attributeIds) {
        this.id = id;
        this.attributeIds = attributeIds;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getAttributeIds() {
        return attributeIds;
    }

    public void setAttributeIds(List<Long> attributeIds) {
        this.attributeIds = attributeIds;
    }
}
