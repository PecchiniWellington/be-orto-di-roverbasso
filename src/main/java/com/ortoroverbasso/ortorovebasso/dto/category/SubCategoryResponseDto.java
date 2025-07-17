package com.ortoroverbasso.ortorovebasso.dto.category;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubCategoryResponseDto {
    private Long id;
    private String name;
    private String slug;
    private Long parentCategoryId;

    public SubCategoryResponseDto() {
    }

    public SubCategoryResponseDto(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public SubCategoryResponseDto(Long id, String name, String slug, Long parentCategoryId) {
        this.id = id;
        this.name = name;
        this.slug = slug;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SubCategoryResponseDto that = (SubCategoryResponseDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SubCategoryResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", parentCategoryId=" + parentCategoryId +
                '}';
    }
}
