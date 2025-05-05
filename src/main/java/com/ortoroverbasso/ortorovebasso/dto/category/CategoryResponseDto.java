package com.ortoroverbasso.ortorovebasso.dto.category;

import java.util.Set;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;

public class CategoryResponseDto {
    private Long id;
    private String name;
    private Set<CategoryResponseDto> subCategories; // Sottocategorie
    private Set<ProductResponseDto> products; // Prodotti associati alla categoria
    private Long parentCategoryId; // ID della categoria padre (per evitare il ciclo infinito)

    // Getter e Setter
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
        this.subCategories = subCategories;
    }

    public Set<ProductResponseDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductResponseDto> products) {
        this.products = products;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
