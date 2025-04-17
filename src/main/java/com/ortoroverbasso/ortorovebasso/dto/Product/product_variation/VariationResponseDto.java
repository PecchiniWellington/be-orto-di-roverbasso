package com.ortoroverbasso.ortorovebasso.dto.product.product_variation;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesResponseDto;

public class VariationResponseDto {
    private Long id;
    private List<ProductAttributesResponseDto> attributes;

    public VariationResponseDto() {
    }

    public VariationResponseDto(
            Long id,
            List<ProductAttributesResponseDto> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductAttributesResponseDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttributesResponseDto> attributes) {
        this.attributes = attributes;
    }
}
