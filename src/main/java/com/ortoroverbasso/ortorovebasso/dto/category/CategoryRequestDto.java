package com.ortoroverbasso.ortorovebasso.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequestDto {

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String name;

    private Long parentCategoryId;

    @NotBlank(message = "Category slug is required")
    @Size(min = 2, max = 100, message = "Category slug must be between 2 and 100 characters")
    private String slug;

    public CategoryRequestDto() {
    }

    public CategoryRequestDto(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public CategoryRequestDto(String name, String slug, Long parentCategoryId) {
        this.name = name;
        this.slug = slug;
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
