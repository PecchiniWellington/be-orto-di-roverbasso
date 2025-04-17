package com.ortoroverbasso.ortorovebasso.entity.product.product_pricing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductPricingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String sku;
    private String wholesalePrice;
    private double retailPrice;
    private Double inShopsPrice;

    public ProductPricingEntity() {
    }

    public ProductPricingEntity(
            Long id,
            String sku,
            String wholesalePrice,
            double retailPrice,
            Double inShopsPrice) {
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

    public String getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(String wholesalePrice) {
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
