package com.ortoroverbasso.ortorovebasso.service.category.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.category.CategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.mapper.category.CategoryMapper;
import com.ortoroverbasso.ortorovebasso.repository.category.CategoryRepository;
import com.ortoroverbasso.ortorovebasso.service.category.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDto getCategory(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return CategoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        CategoryEntity category = CategoryMapper.toEntity(categoryRequestDto);
        categoryRepository.save(category);
        return CategoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryRequestDto.getName());

        // Imposta la categoria padre se presente
        if (categoryRequestDto.getParentCategoryId() != null) {
            CategoryEntity parentCategory = new CategoryEntity();
            parentCategory.setId(categoryRequestDto.getParentCategoryId());
            category.setParentCategory(parentCategory);
        }

        categoryRepository.save(category);
        return CategoryMapper.toResponse(category);
    }

    @Override
    public void deleteCategory(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Disassociare i prodotti dalla sottocategoria, se presente
        category.getSubCategories().forEach(subCategory -> {
            subCategory.setParentCategory(null); // Disassociamo la sottocategoria dalla categoria principale
            // Se hai una relazione con i prodotti, disassocia anche i prodotti
            subCategory.getProducts().forEach(product -> product.setCategory(null));
        });

        // Procediamo con la cancellazione della categoria e delle sue sottocategorie
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponseDto createSubCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
        // Verifica se la categoria principale esiste
        CategoryEntity parentCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Creazione della sottocategoria
        CategoryEntity subCategory = new CategoryEntity();
        subCategory.setName(categoryRequestDto.getName());
        subCategory.setParentCategory(parentCategory);

        // Salva nel database
        categoryRepository.save(subCategory);

        return CategoryMapper.toResponse(subCategory);
    }

}
