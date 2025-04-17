package com.ortoroverbasso.ortorovebasso.entity.product.product_stock;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class ProductStockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_entity_id")
    private List<ProductStock> stocks;

    public ProductStockEntity() {
    }

    public ProductStockEntity(Long id, String sku, List<ProductStock> stocks) {
        this.id = id;
        this.sku = sku;
        this.stocks = stocks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<ProductStock> getStocks() {
        return stocks;
    }

    public void setStocks(List<ProductStock> stocks) {
        this.stocks = stocks;
    }
}
