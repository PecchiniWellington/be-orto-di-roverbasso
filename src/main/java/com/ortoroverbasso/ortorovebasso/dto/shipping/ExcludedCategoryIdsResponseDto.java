package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class ExcludedCategoryIdsResponseDto {

    private String categoryIds;

    // Default constructor
    public ExcludedCategoryIdsResponseDto() {
    }

    // All-args constructor
    public ExcludedCategoryIdsResponseDto(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    // Getters and Setters
    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }
}
