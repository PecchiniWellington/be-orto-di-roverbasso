package com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.validation.constraints.NotNull;

public class ProductLargeQuantityPriceRequestDto {
    private Long id;
    private ProductEntity product;

    @NotNull(message = "ProductId is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Price is required")
    private Double price;

    /*
     * @NotNull(message = "Product Variation is required")
     * private Long productVariationId;
     */

    // Default constructor
    public ProductLargeQuantityPriceRequestDto() {
    }

    // Parameterized constructor
    public ProductLargeQuantityPriceRequestDto(
            Long id,
            Integer quantity,
            Double price,
            Long productId) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
