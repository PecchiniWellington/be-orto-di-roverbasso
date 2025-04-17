package com.ortoroverbasso.ortorovebasso.entity.product.product_attributes;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_variation.VariationEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_attributes")
public class ProductAttributesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int attributeGroup;
    private String name;
    private String isoCode;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "variation_id")
    private VariationEntity variation;

    // Default constructor
    public ProductAttributesEntity() {
    }

    // All-args constructor
    public ProductAttributesEntity(long id, int attributeGroup, String name, String isoCode) {
        this.id = id;
        this.attributeGroup = attributeGroup;
        this.name = name;
        this.isoCode = isoCode;
    }

    // Getter and Setter for id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter and Setter for product
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    // Getter and Setter for attributeGroup
    public int getAttributeGroup() {
        return attributeGroup;
    }

    public void setAttributeGroup(int attributeGroup) {
        this.attributeGroup = attributeGroup;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for isoCode
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    // Getter and Setter for variation
    public VariationEntity getVariation() {
        return variation;
    }

    public void setVariation(VariationEntity variation) {
        this.variation = variation;
    }
}
