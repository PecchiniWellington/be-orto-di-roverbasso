package com.ortoroverbasso.ortorovebasso.dto.cart;

public class CartItemDto {
    private Long productId;
    private String productName;
    private int quantity;
    private String price;

    // Modifica del costruttore per prendere il nome dal ProductInformation
    public CartItemDto(Long productId, String productName, int quantity, String price) {
        this.productId = productId;
        this.productName = productName != null ? productName : "Default Name"; // Fallback per il nome
        this.quantity = quantity;
        this.price = price != null ? price : "0.00"; // Fallback per il prezzo
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
