package com.ortoroverbasso.ortorovebasso.dto.product.product_stock;

import java.util.List;

public class ProductStockResponseDto {
    private int id;
    private String sku;
    private List<ProductStockDto> stocks;

    // Costruttore senza argomenti
    public ProductStockResponseDto() {
    }

    // Costruttore con parametri
    public ProductStockResponseDto(int id, String sku, List<ProductStockDto> stocks) {
        this.id = id;
        this.sku = sku;
        this.stocks = stocks;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<ProductStockDto> getStocks() {
        return stocks;
    }

    public void setStocks(List<ProductStockDto> stocks) {
        this.stocks = stocks;
    }
}
