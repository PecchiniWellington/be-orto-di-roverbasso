package com.ortoroverbasso.ortorovebasso.dto.shipping;

public class ExcludedCategoryIdsRequestDto {

    private String categoryIds;

    // Default constructor
    public ExcludedCategoryIdsRequestDto() {
    }

    // All-args constructor
    public ExcludedCategoryIdsRequestDto(String categoryIds) {
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
