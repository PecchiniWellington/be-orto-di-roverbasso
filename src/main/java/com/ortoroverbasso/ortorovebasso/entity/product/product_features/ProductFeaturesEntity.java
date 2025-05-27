package com.ortoroverbasso.ortorovebasso.entity.product.product_features;

import java.util.Objects;
import java.util.Set;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class ProductFeaturesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String feature;
    private String value;

    @ManyToMany(mappedBy = "productFeatures")
    private Set<ProductEntity> products;

    // Default constructor
    public ProductFeaturesEntity() {
    }

    // All-args constructor
    public ProductFeaturesEntity(
            Long id,
            String feature,
            String value) {
        this.id = id;
        this.feature = feature;
        this.value = value;

    }

    // Getter and Setter for feature
    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    // Getter and Setter for value
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // Getter and Setter for products
    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Override equals and hashCode for proper comparison
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductFeaturesEntity that = (ProductFeaturesEntity) o;
        return Objects.equals(feature, that.feature) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feature, value);
    }

    // Override toString for better readability
    @Override
    public String toString() {
        return "ProductFeaturesEntity{" +
                "feature='" + feature + '\'' +
                ", value='" + value + '\'' +
                '}';

    }
}
