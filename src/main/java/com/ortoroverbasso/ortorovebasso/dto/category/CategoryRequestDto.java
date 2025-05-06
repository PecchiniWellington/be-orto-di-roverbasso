package com.ortoroverbasso.ortorovebasso.dto.category;

public class CategoryRequestDto {
    private String name;
    private Long parentCategoryId;
    private String slug;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

}
