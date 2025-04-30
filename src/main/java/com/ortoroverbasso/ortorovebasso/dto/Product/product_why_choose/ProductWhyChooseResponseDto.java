package com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose;

import java.util.List;

public class ProductWhyChooseResponseDto {
    private Long id;
    private String value;

    private List<Long> productIds;

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
