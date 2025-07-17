package com.ortoroverbasso.ortorovebasso.service.category;

import java.util.Set;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.category.CategoryResponseDto;

public interface ICategoryService {

    /**
     * Recupera una categoria per ID con tutti i dettagli (sottocategorie e
     * prodotti)
     */
    CategoryResponseDto getCategory(Long id);

    /**
     * Recupera una categoria per slug con tutti i dettagli
     */
    CategoryResponseDto getCategoryBySlug(String slug);

    /**
     * Recupera tutte le categorie radice con gerarchia completa
     */
    Set<CategoryResponseDto> getAllCategories();

    /**
     * Recupera solo le categorie radice (senza sottocategorie)
     */
    Set<CategoryResponseDto> getRootCategories();

    /**
     * Recupera le sottocategorie di una categoria specifica
     */
    Set<CategoryResponseDto> getSubCategories(Long parentCategoryId);

    /**
     * Crea una nuova categoria
     */
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    /**
     * Aggiorna una categoria esistente
     */
    CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto);

    /**
     * Crea una sottocategoria
     */
    CategoryResponseDto createSubCategory(Long parentCategoryId, CategoryRequestDto categoryRequestDto);

    /**
     * Elimina una categoria e tutte le sue sottocategorie
     */
    void deleteCategory(Long id);

    /**
     * Verifica se una categoria ha sottocategorie
     */
    boolean hasSubCategories(Long categoryId);

    /**
     * Verifica se una categoria ha prodotti
     */
    boolean hasProducts(Long categoryId);
}
