package com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price;

public class ProductLargeQuantityPriceResponseDto {
    private Long id;
    private Integer quantity;
    private Double price;

    // Default constructor
    public ProductLargeQuantityPriceResponseDto() {
    }

    // Parameterized constructor
    public ProductLargeQuantityPriceResponseDto(
            Long id,
            Integer quantity,
            Double price) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
