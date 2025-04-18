package com.ortoroverbasso.ortorovebasso.entity.shipping;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "excluded_product_references")
public class ExcludedProductReferenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    // Default constructor
    public ExcludedProductReferenceEntity() {
    }

    // Constructor with String parameter
    public ExcludedProductReferenceEntity(String reference) {
        this.reference = reference;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
