package com.ortoroverbasso.ortorovebasso.dto.category;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String slug;
    private Set<CategoryResponseDto> subCategories = new HashSet<>();
    private Set<ProductCategoryResponseDto> products = new HashSet<>();
    private Long parentCategoryId;

    public CategoryResponseDto() {
    }

    public CategoryResponseDto(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public CategoryResponseDto(Long id, String name, String slug, Set<CategoryResponseDto> subCategories,
            Set<ProductCategoryResponseDto> products, Long parentCategoryId) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.subCategories = subCategories;
        this.products = products;
        this.parentCategoryId = parentCategoryId;
    }

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

    public Set<CategoryResponseDto> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<CategoryResponseDto> subCategories) {
        this.subCategories = subCategories != null ? subCategories : new HashSet<>();
    }

    public Set<ProductCategoryResponseDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductCategoryResponseDto> products) {
        this.products = products != null ? products : new HashSet<>();
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    // Metodi di utilità
    public boolean hasSubCategories() {
        return subCategories != null && !subCategories.isEmpty();
    }

    public boolean hasProducts() {
        return products != null && !products.isEmpty();
    }

    public boolean isRootCategory() {
        return parentCategoryId == null;
    }
}
