package com.ortoroverbasso.ortorovebasso.dto.category;

import java.util.Set;

public class CategoryResponseDto {
    private Long id;
    private String name;
    private Set<CategoryResponseDto> subCategories; // Sottocategorie
    private Set<ProductCategoryResponseDto> products; // Prodotti associati alla categoria
    private Long parentCategoryId; // ID della categoria padre (per evitare il ciclo infinito)

    public CategoryResponseDto() {

    }

    // Costruttore con parametri
    public CategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryResponseDto(Long id, String name, Set<CategoryResponseDto> subCategories,
            Set<ProductCategoryResponseDto> products, Long parentCategoryId) {
        this.id = id;
        this.name = name;
        this.subCategories = subCategories;
        this.products = products;
        this.parentCategoryId = parentCategoryId;
    }

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

    public Set<ProductCategoryResponseDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductCategoryResponseDto> products) {
        this.products = products;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
