package com.ortoroverbasso.ortorovebasso.dto.product.product_pricing;

public class ProductPricingRequestDto {
    private Long id;
    private String sku;
    private double wholesalePrice;
    private double retailPrice;
    private double inShopsPrice;

    public ProductPricingRequestDto() {
    }

    public ProductPricingRequestDto(
            Long id,
            String sku,
            double wholesalePrice,
            double retailPrice,
            double inShopsPrice) {
        this.id = id;
        this.sku = sku;
        this.wholesalePrice = wholesalePrice;
        this.retailPrice = retailPrice;
        this.inShopsPrice = inShopsPrice;
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

    public double getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public double getInShopsPrice() {
        return inShopsPrice;
    }

    public void setInShopsPrice(double inShopsPrice) {
        this.inShopsPrice = inShopsPrice;
    }
}
