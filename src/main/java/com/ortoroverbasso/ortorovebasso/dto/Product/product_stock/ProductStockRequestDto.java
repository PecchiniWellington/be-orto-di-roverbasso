package com.ortoroverbasso.ortorovebasso.dto.product.product_stock;

import java.util.List;

public class ProductStockRequestDto {

    private String sku;
    private List<ProductStockDto> stocks;

    // Costruttore senza argomenti
    public ProductStockRequestDto() {
    }

    // Costruttore con argomenti
    public ProductStockRequestDto(
            String sku,

            List<ProductStockDto> stocks) {
        this.sku = sku;

        this.stocks = stocks;
    }

    // Getter e Setter
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
