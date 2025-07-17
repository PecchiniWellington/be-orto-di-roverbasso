package com.ortoroverbasso.ortorovebasso.service.category.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.category.CategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.exception.CategoryNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.category.CategoryMapper;
import com.ortoroverbasso.ortorovebasso.repository.category.CategoryRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.service.category.CategoryValidationService;
import com.ortoroverbasso.ortorovebasso.service.category.ICategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryValidationService validationService;

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategory(Long id) {
        logger.debug("Fetching category with id: {}", id);

        CategoryEntity category = categoryRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        return CategoryMapper.toResponseWithSubCategories(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryBySlug(String slug) {
        logger.debug("Fetching category with slug: {}", slug);

        CategoryEntity category = categoryRepository.findBySlugWithDetails(slug)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with slug: " + slug));

        return CategoryMapper.toResponseWithSubCategories(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<CategoryResponseDto> getAllCategories() {
        logger.debug("Fetching all categories with full hierarchy");

        return categoryRepository.findRootCategoriesWithFullHierarchy()
                .stream()
                .map(CategoryMapper::toResponseWithSubCategories)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<CategoryResponseDto> getRootCategories() {
        logger.debug("Fetching root categories only");

        return categoryRepository.findRootCategories()
                .stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<CategoryResponseDto> getSubCategories(Long parentCategoryId) {
        logger.debug("Fetching subcategories for parent category id: {}", parentCategoryId);

        // Verifica che la categoria padre esista
        if (!categoryRepository.existsById(parentCategoryId)) {
            throw new CategoryNotFoundException("Parent category not found with id: " + parentCategoryId);
        }

        return categoryRepository.findByParentCategoryId(parentCategoryId)
                .stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        logger.debug("Creating new category: {}", categoryRequestDto.getName());

        // Validazioni
        validationService.validateCategoryRequest(categoryRequestDto);
        validationService.validateSlugUniqueness(categoryRequestDto.getSlug());

        CategoryEntity parentCategory = validationService
                .validateParentCategory(categoryRequestDto.getParentCategoryId());

        // Crea la categoria
        CategoryEntity category = CategoryMapper.toEntity(categoryRequestDto);

        if (parentCategory != null) {
            category.setParentCategory(parentCategory);
        }

        CategoryEntity savedCategory = categoryRepository.save(category);
        logger.info("Category created successfully with id: {}", savedCategory.getId());

        return CategoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
        logger.debug("Updating category with id: {}", id);

        // Validazioni
        validationService.validateCategoryRequest(categoryRequestDto);
        validationService.validateSlugUniquenessForUpdate(categoryRequestDto.getSlug(), id);
        validationService.validateNoCyclicDependency(id, categoryRequestDto.getParentCategoryId());

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        CategoryEntity parentCategory = validationService
                .validateParentCategory(categoryRequestDto.getParentCategoryId());

        // Aggiorna i dati
        CategoryMapper.updateEntity(category, categoryRequestDto);
        category.setParentCategory(parentCategory);

        CategoryEntity updatedCategory = categoryRepository.save(category);
        logger.info("Category updated successfully with id: {}", updatedCategory.getId());

        return CategoryMapper.toResponse(updatedCategory);
    }

    @Override
    public CategoryResponseDto createSubCategory(Long parentCategoryId, CategoryRequestDto categoryRequestDto) {
        logger.debug("Creating subcategory for parent category id: {}", parentCategoryId);

        // Imposta l'ID della categoria padre nel DTO
        categoryRequestDto.setParentCategoryId(parentCategoryId);

        return createCategory(categoryRequestDto);
    }

    @Override
    public void deleteCategory(Long id) {
        logger.debug("Deleting category with id: {}", id);

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        // Disassocia i prodotti dalla categoria
        dissociateProductsFromCategory(category);

        // Elimina ricorsivamente le sottocategorie
        deleteSubCategoriesRecursively(category);

        // Elimina la categoria principale
        categoryRepository.delete(category);

        logger.info("Category deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasSubCategories(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryEntity::hasSubCategories)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasProducts(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryEntity::hasProducts)
                .orElse(false);
    }

    /**
     * Disassocia tutti i prodotti da una categoria
     */
    private void dissociateProductsFromCategory(CategoryEntity category) {
        logger.debug("Dissociating {} products from category id: {}",
                category.getProducts().size(), category.getId());

        category.getProducts().forEach(product -> {
            product.setCategory(null);
            if (product.getProductInformation() != null) {
                product.getProductInformation().setProduct(null);
            }
            productRepository.save(product);
        });
    }

    /**
     * Elimina ricorsivamente tutte le sottocategorie
     */
    private void deleteSubCategoriesRecursively(CategoryEntity category) {
        logger.debug("Deleting subcategories for category id: {}", category.getId());

        category.getSubCategories().forEach(subCategory -> {
            // Disassocia i prodotti dalla sottocategoria
            dissociateProductsFromCategory(subCategory);

            // Elimina ricorsivamente le sottocategorie della sottocategoria
            deleteSubCategoriesRecursively(subCategory);

            // Elimina la sottocategoria
            categoryRepository.delete(subCategory);
        });
    }
}
