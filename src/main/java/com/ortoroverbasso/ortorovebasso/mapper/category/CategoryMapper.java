package com.ortoroverbasso.ortorovebasso.mapper.category;

import java.util.Set;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.category.CategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.category.ProductCategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;

public class CategoryMapper {

    public static CategoryEntity toEntity(CategoryRequestDto categoryRequestDto) {
        if (categoryRequestDto == null) {
            return null;
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(categoryRequestDto.getName());
        category.setSlug(categoryRequestDto.getSlug());

        return category;
    }

    public static CategoryResponseDto toResponse(CategoryEntity category) {
        if (category == null) {
            return null;
        }

        CategoryResponseDto response = new CategoryResponseDto();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setSlug(category.getSlug());

        // Mappa i prodotti
        Set<ProductCategoryResponseDto> productDtos = category.getProducts()
                .stream()
                .map(product -> new ProductCategoryResponseDto(
                        product.getId(),
                        product.getProductInformation() != null
                                ? product.getProductInformation().getName()
                                : ""))
                .collect(Collectors.toSet());
        response.setProducts(productDtos);

        // Imposta l'ID della categoria padre
        if (category.getParentCategory() != null) {
            response.setParentCategoryId(category.getParentCategory().getId());
        }

        return response;
    }

    public static CategoryResponseDto toResponseWithSubCategories(CategoryEntity category) {
        if (category == null) {
            return null;
        }

        CategoryResponseDto response = toResponse(category);

        // Mappa ricorsivamente le sottocategorie
        Set<CategoryResponseDto> subCategoryDtos = category.getSubCategories()
                .stream()
                .map(CategoryMapper::toResponseWithSubCategories)
                .collect(Collectors.toSet());
        response.setSubCategories(subCategoryDtos);

        return response;
    }

    public static void updateEntity(CategoryEntity category, CategoryRequestDto categoryRequestDto) {
        if (category == null || categoryRequestDto == null) {
            return;
        }

        category.setName(categoryRequestDto.getName());
        category.setSlug(categoryRequestDto.getSlug());
    }
}
