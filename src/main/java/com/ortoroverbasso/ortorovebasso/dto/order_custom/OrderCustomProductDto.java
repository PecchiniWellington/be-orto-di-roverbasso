package com.ortoroverbasso.ortorovebasso.dto.order_custom;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;

public class OrderCustomProductDto {

    private ProductResponseDto product;
    private int quantity;

    // Getters e Setters
    public ProductResponseDto getProduct() {
        return product;
    }

    public void setProduct(ProductResponseDto product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
