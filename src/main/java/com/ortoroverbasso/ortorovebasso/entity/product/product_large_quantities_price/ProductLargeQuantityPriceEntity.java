package com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price;

import com.ortoroverbasso.ortorovebasso.entity.product.product_variation.ProductVariationEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductLargeQuantityPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariationEntity productVariation;

    public ProductLargeQuantityPriceEntity() {

    }

    public ProductLargeQuantityPriceEntity(Integer quantity, Double price) {
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductVariationEntity getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariationEntity productVariation) {
        this.productVariation = productVariation;
    }
}
