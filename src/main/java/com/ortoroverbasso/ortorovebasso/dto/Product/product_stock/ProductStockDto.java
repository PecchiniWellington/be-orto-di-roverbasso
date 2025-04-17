package com.ortoroverbasso.ortorovebasso.dto.product.product_stock;

public class ProductStockDto {
    private int quantity;
    private int minHandlingDays;
    private int maxHandlingDays;
    private int warehouse;

    // Constructor, getters, setters
    public ProductStockDto() {
    }

    public ProductStockDto(int quantity, int minHandlingDays, int maxHandlingDays, int warehouse) {
        this.quantity = quantity;
        this.minHandlingDays = minHandlingDays;
        this.maxHandlingDays = maxHandlingDays;
        this.warehouse = warehouse;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinHandlingDays() {
        return minHandlingDays;
    }

    public void setMinHandlingDays(int minHandlingDays) {
        this.minHandlingDays = minHandlingDays;
    }

    public int getMaxHandlingDays() {
        return maxHandlingDays;
    }

    public void setMaxHandlingDays(int maxHandlingDays) {
        this.maxHandlingDays = maxHandlingDays;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }
}
