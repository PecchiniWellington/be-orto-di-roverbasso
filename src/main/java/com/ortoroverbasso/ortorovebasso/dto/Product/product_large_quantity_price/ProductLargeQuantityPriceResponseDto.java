package com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price;

import java.math.BigDecimal;

public class ProductLargeQuantityPriceResponseDto {
    private Long id;
    private Integer quantity;
    private BigDecimal price;

    // Default constructor
    public ProductLargeQuantityPriceResponseDto() {
    }

    // Parameterized constructor
    public ProductLargeQuantityPriceResponseDto(
            Long id,
            Integer quantity,
            BigDecimal price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
