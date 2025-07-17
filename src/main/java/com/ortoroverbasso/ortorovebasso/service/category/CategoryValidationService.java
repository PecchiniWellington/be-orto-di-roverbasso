package com.ortoroverbasso.ortorovebasso.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.exception.CategoryNotFoundException;
import com.ortoroverbasso.ortorovebasso.repository.category.CategoryRepository;

@Service
public class CategoryValidationService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Valida che lo slug sia unico per una nuova categoria
     */
    public void validateSlugUniqueness(String slug) {
        if (categoryRepository.existsBySlug(slug)) {
            throw new IllegalArgumentException("Category with slug '" + slug + "' already exists");
        }
    }

    /**
     * Valida che lo slug sia unico per un aggiornamento (escludendo la categoria
     * corrente)
     */
    public void validateSlugUniquenessForUpdate(String slug, Long categoryId) {
        if (categoryRepository.existsBySlugAndIdNot(slug, categoryId)) {
            throw new IllegalArgumentException("Category with slug '" + slug + "' already exists");
        }
    }

    /**
     * Valida che la categoria padre esista
     */
    public CategoryEntity validateParentCategory(Long parentCategoryId) {
        if (parentCategoryId == null) {
            return null;
        }

        return categoryRepository.findById(parentCategoryId)
                .orElseThrow(
                        () -> new CategoryNotFoundException("Parent category not found with id: " + parentCategoryId));
    }

    /**
     * Valida che non si crei un ciclo nella gerarchia delle categorie
     */
    public void validateNoCyclicDependency(Long categoryId, Long parentCategoryId) {
        if (categoryId == null || parentCategoryId == null) {
            return;
        }

        if (categoryId.equals(parentCategoryId)) {
            throw new IllegalArgumentException("Category cannot be its own parent");
        }

        // Verifica che la categoria padre non sia una sottocategoria della categoria
        // corrente
        CategoryEntity parentCategory = categoryRepository.findById(parentCategoryId)
                .orElseThrow(
                        () -> new CategoryNotFoundException("Parent category not found with id: " + parentCategoryId));

        if (isDescendant(categoryId, parentCategory)) {
            throw new IllegalArgumentException(
                    "Cannot create cyclic dependency: parent category is a descendant of the current category");
        }
    }

    /**
     * Verifica se una categoria è discendente di un'altra
     */
    private boolean isDescendant(Long ancestorId, CategoryEntity category) {
        if (category == null) {
            return false;
        }

        CategoryEntity parent = category.getParentCategory();
        while (parent != null) {
            if (parent.getId().equals(ancestorId)) {
                return true;
            }
            parent = parent.getParentCategory();
        }
        return false;
    }

    /**
     * Valida i dati della richiesta
     */
    public void validateCategoryRequest(CategoryRequestDto request) {
        if (request == null) {
            throw new IllegalArgumentException("Category request cannot be null");
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        if (request.getSlug() == null || request.getSlug().trim().isEmpty()) {
            throw new IllegalArgumentException("Category slug cannot be empty");
        }

        // Valida formato slug (solo lettere, numeri, trattini)
        if (!request.getSlug().matches("^[a-z0-9-]+$")) {
            throw new IllegalArgumentException(
                    "Category slug can only contain lowercase letters, numbers, and hyphens");
        }
    }
}
