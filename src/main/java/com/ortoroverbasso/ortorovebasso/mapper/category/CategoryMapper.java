package com.ortoroverbasso.ortorovebasso.mapper.category;

import java.util.Set;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.category.CategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;

public class CategoryMapper {

    public static CategoryEntity toEntity(CategoryRequestDto categoryRequestDto) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryRequestDto.getName());

        // Imposta la categoria padre se presente
        if (categoryRequestDto.getParentCategoryId() != null) {
            CategoryEntity parentCategory = new CategoryEntity();
            parentCategory.setId(categoryRequestDto.getParentCategoryId());
            category.setParentCategory(parentCategory);
        }

        return category;
    }

    public static CategoryResponseDto toResponse(CategoryEntity category) {
        CategoryResponseDto response = new CategoryResponseDto();
        response.setId(category.getId());
        response.setName(category.getName());

        // Mappa le sottocategorie della categoria
        Set<CategoryResponseDto> subCategoryResponseDtos = category.getSubCategories().stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toSet());
        response.setSubCategories(subCategoryResponseDtos);

        // Mappa i prodotti della categoria
        Set<ProductResponseDto> productResponseDtos = category.getProducts().stream()
                .map(ProductMapper::toResponseDto)
                .collect(Collectors.toSet());
        response.setProducts(productResponseDtos);

        // Aggiungi l'ID della categoria padre alla risposta
        if (category.getParentCategory() != null) {
            response.setParentCategoryId(category.getParentCategory().getId());
        }

        return response;
    }
}
