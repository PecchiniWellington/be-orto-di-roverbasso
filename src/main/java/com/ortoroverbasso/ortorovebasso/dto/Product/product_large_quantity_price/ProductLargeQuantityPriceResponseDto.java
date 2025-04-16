package com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

public class ProductLargeQuantityPriceResponseDto {
    private Long id;
    private Integer quantity;
    private Double price;
    private ProductEntity product;

    // Default constructor
    public ProductLargeQuantityPriceResponseDto() {
    }

    // Parameterized constructor
    public ProductLargeQuantityPriceResponseDto(Long id, Integer quantity, Double price, ProductEntity product) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
