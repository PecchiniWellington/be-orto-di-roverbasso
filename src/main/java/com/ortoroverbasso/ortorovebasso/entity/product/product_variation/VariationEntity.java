package com.ortoroverbasso.ortorovebasso.entity.product.product_variation;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.entity.product.product_attributes.ProductAttributesEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "variations")
public class VariationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "variation", fetch = FetchType.LAZY)
    private List<ProductAttributesEntity> attributes;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductAttributesEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttributesEntity> attributes) {
        this.attributes = attributes;
    }
}
