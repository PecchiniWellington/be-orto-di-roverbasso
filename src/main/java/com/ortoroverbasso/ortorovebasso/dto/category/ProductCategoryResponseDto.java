package com.ortoroverbasso.ortorovebasso.dto.category;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCategoryResponseDto {
    private Long id;
    private String name;

    public ProductCategoryResponseDto() {
    }

    public ProductCategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductCategoryResponseDto that = (ProductCategoryResponseDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductCategoryResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
