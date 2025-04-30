package com.ortoroverbasso.ortorovebasso.entity.product.product_why_choose;

import java.util.Objects;
import java.util.Set;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class ProductWhyChooseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String value;

    @ManyToMany
    @JoinTable(name = "product_whychoose", joinColumns = @JoinColumn(name = "whychoose_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<ProductEntity> products;

    // Default constructor
    public ProductWhyChooseEntity() {
    }

    // Constructor with args
    public ProductWhyChooseEntity(String value) {
        this.value = value;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductWhyChooseEntity that = (ProductWhyChooseEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }
}
