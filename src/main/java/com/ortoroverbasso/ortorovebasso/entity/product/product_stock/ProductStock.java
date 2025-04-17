package com.ortoroverbasso.ortorovebasso.entity.product.product_stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private int minHandlingDays;
    private int maxHandlingDays;
    private int warehouse;

    @ManyToOne
    @JoinColumn(name = "stock_entity_id")
    private ProductStockEntity stockEntity;

    // Constructor
    public ProductStock() {
    }

    public ProductStock(int quantity, int minHandlingDays, int maxHandlingDays, int warehouse) {
        this.quantity = quantity;
        this.minHandlingDays = minHandlingDays;
        this.maxHandlingDays = maxHandlingDays;
        this.warehouse = warehouse;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProductStockEntity getStockEntity() {
        return stockEntity;
    }

    public void setStockEntity(ProductStockEntity stockEntity) {
        this.stockEntity = stockEntity;
    }
}
