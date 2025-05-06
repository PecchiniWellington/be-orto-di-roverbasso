package com.ortoroverbasso.ortorovebasso.mapper.category;

import java.util.Set;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.category.CategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.category.ProductCategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;

public class CategoryMapper {

    public static CategoryEntity toEntity(CategoryRequestDto categoryRequestDto) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryRequestDto.getName());
        category.setSlug(categoryRequestDto.getSlug());

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
        response.setSlug(category.getSlug());

        Set<ProductCategoryResponseDto> productCategoryResponseDtos = category.getProducts()
                .stream()
                .map(product -> {
                    String productName = (product.getProductInformation() != null)
                            ? product.getProductInformation().getName()
                            : "";
                    return new ProductCategoryResponseDto(product.getId(), productName);
                })
                .collect(Collectors.toSet());

        response.setProducts(productCategoryResponseDtos);

        if (category.getParentCategory() != null) {
            response.setParentCategoryId(category.getParentCategory().getId());
        }

        return response;
    }
}
