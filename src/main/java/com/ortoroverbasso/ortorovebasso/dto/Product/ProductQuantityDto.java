package com.ortoroverbasso.ortorovebasso.dto.product;

public class ProductQuantityDto {

    private Long productId;
    private int quantity;

    // Getters e Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
