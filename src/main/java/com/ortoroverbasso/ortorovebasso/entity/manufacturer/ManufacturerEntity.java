package com.ortoroverbasso.ortorovebasso.entity.manufacturer;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ManufacturerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String urlImage;
    private Integer reference;

    // Default constructor
    public ManufacturerEntity() {
    }

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
    private List<ProductEntity> products;

    // All-args constructor
    public ManufacturerEntity(
            Long id,
            String name,
            String urlImage,
            Integer reference) {
        this.id = id;
        this.name = name;
        this.urlImage = urlImage;
        this.reference = reference;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
}
