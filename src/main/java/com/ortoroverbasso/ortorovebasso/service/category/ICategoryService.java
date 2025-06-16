package com.ortoroverbasso.ortorovebasso.service.category;

import java.util.Set;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.category.CategoryResponseDto;

public interface ICategoryService {
    CategoryResponseDto getCategory(Long id);

    Set<CategoryResponseDto> getAllCategories();

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto);

    CategoryResponseDto createSubCategory(Long categoryId, CategoryRequestDto categoryRequestDto);

    void deleteCategory(Long id);
}
