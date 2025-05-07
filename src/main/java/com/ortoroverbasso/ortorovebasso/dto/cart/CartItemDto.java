package com.ortoroverbasso.ortorovebasso.dto.cart;

public class CartItemDto {
    private Long productId;
    private String productName;
    private int quantity;
    private Double price;

    // Modifica del costruttore per prendere il nome dal ProductInformation
    public CartItemDto(Long productId, String productName, int quantity, Double price) {
        this.productId = productId;
        this.productName = productName != null ? productName : "Default Name";
        this.quantity = quantity;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
