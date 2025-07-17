package com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price;

import java.math.BigDecimal;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.validation.constraints.NotNull;

public class ProductLargeQuantityPriceRequestDto {
    private Long id;
    private ProductEntity product;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    // Default constructor
    public ProductLargeQuantityPriceRequestDto() {
    }

    // Parameterized constructor
    public ProductLargeQuantityPriceRequestDto(
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

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

}
