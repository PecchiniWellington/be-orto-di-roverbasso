package com.ortoroverbasso.ortorovebasso.entity.product;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ProductPricingInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private double wholesalePrice;
    private double retailPrice;
    private double inShopsPrice;

    @OneToMany(mappedBy = "productPricingInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPriceLargeQuantityEntity> priceLargeQuantities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "sku", referencedColumnName = "sku", insertable = false, updatable = false)
    private ProductEntity product;

    // Getter e setter
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public ProductPricingInfoEntity() {
    }

    public ProductPricingInfoEntity(
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

    public List<ProductPriceLargeQuantityEntity> getPriceLargeQuantities() {
        return priceLargeQuantities;
    }

    public void setPriceLargeQuantities(List<ProductPriceLargeQuantityEntity> priceLargeQuantities) {
        this.priceLargeQuantities = priceLargeQuantities;
    }
}
