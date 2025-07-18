package com.ortoroverbasso.ortorovebasso.dto.category;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentCategoryResponseDto {
    private Long id;
    private String name;
    private String slug;

    public ParentCategoryResponseDto() {
    }

    public ParentCategoryResponseDto(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ParentCategoryResponseDto that = (ParentCategoryResponseDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ParentCategoryResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}
