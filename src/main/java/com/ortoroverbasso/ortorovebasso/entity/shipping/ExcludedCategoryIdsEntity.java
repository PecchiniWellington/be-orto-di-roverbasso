package com.ortoroverbasso.ortorovebasso.entity.shipping;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "excluded_category_ids")
public class ExcludedCategoryIdsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryIds;

    // Default constructor
    public ExcludedCategoryIdsEntity() {
    }

    // All-args constructor
    public ExcludedCategoryIdsEntity(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }
}
