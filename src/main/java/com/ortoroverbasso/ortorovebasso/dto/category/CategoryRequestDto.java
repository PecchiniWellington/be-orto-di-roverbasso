package com.ortoroverbasso.ortorovebasso.dto.category;

public class CategoryRequestDto {
    private String name;
    private Long parentCategoryId; // ID della categoria padre, può essere null

    // Getter e Setter

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
}
