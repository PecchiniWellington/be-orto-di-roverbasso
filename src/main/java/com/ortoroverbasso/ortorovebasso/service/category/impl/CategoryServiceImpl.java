package com.ortoroverbasso.ortorovebasso.service.category.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.category.CategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.category.ProductCategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.mapper.category.CategoryMapper;
import com.ortoroverbasso.ortorovebasso.repository.category.CategoryRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.service.category.ICategoryService;

import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public CategoryResponseDto getCategory(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Set<CategoryResponseDto> subCategoriesDtos = category.getSubCategories().stream()
                .filter(sub -> sub.getSubCategories().isEmpty())
                .map(subCategory -> new CategoryResponseDto(
                        subCategory.getId(),
                        subCategory.getName(),
                        subCategory.getSlug()))
                .collect(Collectors.toSet());

        CategoryResponseDto categoryResponseDto = CategoryMapper.toResponse(category);
        categoryResponseDto.setSubCategories(subCategoriesDtos);

        return categoryResponseDto;
    }

    @Transactional
    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAllWithSubCategoriesAndProducts();

        return categories.stream()
                .filter(category -> category.getParentCategory() == null)
                .map(category -> {

                    Set<ProductCategoryResponseDto> productIdAndNameDtos = category.getProducts().stream()
                            .map(product -> {
                                String productName = (product.getProductInformation() != null)
                                        ? product.getProductInformation().getName()
                                        : "";
                                return new ProductCategoryResponseDto(product.getId(), productName);
                            })
                            .collect(Collectors.toSet());

                    Set<CategoryResponseDto> subCategoriesDtos = getSubCategoriesRecursive(category);

                    CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
                    categoryResponseDto.setId(category.getId());
                    categoryResponseDto.setName(category.getName());
                    categoryResponseDto.setSlug(category.getSlug());
                    categoryResponseDto.setProducts(productIdAndNameDtos);
                    categoryResponseDto.setSubCategories(subCategoriesDtos);
                    categoryResponseDto.setParentCategoryId(
                            category.getParentCategory() != null ? category.getParentCategory().getId() : null);

                    return categoryResponseDto;
                })
                .collect(Collectors.toList());
    }

    private Set<CategoryResponseDto> getSubCategoriesRecursive(CategoryEntity category) {
        Set<CategoryResponseDto> subCategoriesDtos = category.getSubCategories().stream()
                .filter(sub -> sub.getSubCategories().isEmpty())
                .map(subCategory -> {
                    Set<ProductCategoryResponseDto> subCategoryProducts = subCategory.getProducts().stream()
                            .map(product -> new ProductCategoryResponseDto(product.getId(),
                                    product.getProductInformation() != null ? product.getProductInformation().getName()
                                            : ""))
                            .collect(Collectors.toSet());

                    Set<CategoryResponseDto> nestedSubCategories = getSubCategoriesRecursive(subCategory);

                    CategoryResponseDto subCategoryDto = new CategoryResponseDto();
                    subCategoryDto.setId(subCategory.getId());
                    subCategoryDto.setName(subCategory.getName());
                    subCategoryDto.setSlug(subCategory.getSlug());
                    subCategoryDto.setProducts(subCategoryProducts);
                    subCategoryDto.setSubCategories(nestedSubCategories);
                    subCategoryDto.setParentCategoryId(subCategory.getParentCategory() != null
                            ? subCategory.getParentCategory().getId()
                            : null);

                    return subCategoryDto;
                })
                .collect(Collectors.toSet());

        return subCategoriesDtos;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        CategoryEntity category = CategoryMapper.toEntity(categoryRequestDto);

        if (categoryRequestDto.getParentCategoryId() != null) {
            CategoryEntity parentCategory = categoryRepository.findById(categoryRequestDto.getParentCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent Category not found"));

            category.setParentCategory(parentCategory);

            if (!parentCategory.getSubCategories().contains(category)) {
                parentCategory.getSubCategories().add(category);

            }

            categoryRepository.save(category);
        } else {
            categoryRepository.save(category);
        }

        // Restituisci la categoria come risposta
        return CategoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryRequestDto.getName());
        category.setSlug(categoryRequestDto.getSlug());

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

        // Rimuovi la categoria dai prodotti collegati
        category.getProducts().forEach(product -> {
            if (product.getCategory() != null && product.getCategory().getId().equals(id)) {
                product.setCategory(null);

                if (product.getProductInformation() != null) {
                    product.getProductInformation().setProduct(null);
                }

                productRepository.save(product);
            }
        });

        // Elimina eventuali sottocategorie collegate
        deleteSubCategories(category, null); // se deleteSubCategories richiede una categoria sostitutiva, dovrai
                                             // adattarlo

        categoryRepository.delete(category);
    }

    private void deleteSubCategories(CategoryEntity category, CategoryEntity noCategory) {
        category.getSubCategories().forEach(subCategory -> {
            subCategory.getProducts().forEach(product -> {
                if (product.getCategory() != null) {
                    product.setCategory(noCategory);

                    if (product.getProductInformation() != null) {
                        product.getProductInformation().setProduct(null);
                    }

                    productRepository.save(product); // Aggiorna il prodotto nel database
                }
            });

            deleteSubCategories(subCategory, noCategory);
            categoryRepository.delete(subCategory);
        });
    }

    @Override
    public CategoryResponseDto createSubCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
        CategoryEntity parentCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        CategoryEntity subCategory = new CategoryEntity();
        subCategory.setName(categoryRequestDto.getName());
        subCategory.setSlug(categoryRequestDto.getSlug());
        subCategory.setParentCategory(parentCategory);

        categoryRepository.save(subCategory);

        return CategoryMapper.toResponse(subCategory);
    }

}
