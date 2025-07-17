package com.ortoroverbasso.ortorovebasso.service.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.exception.CategoryNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.category.CategoryMapper;
import com.ortoroverbasso.ortorovebasso.repository.category.CategoryRepository;

@Service
@Transactional(readOnly = true)
public class CategoryHierarchyService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Ottiene il percorso completo (breadcrumb) di una categoria
     * Es: Electronics > Computers > Laptops
     */
    public List<CategoryResponseDto> getCategoryPath(Long categoryId) {
        List<CategoryResponseDto> path = new ArrayList<>();

        Optional<CategoryEntity> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            throw new CategoryNotFoundException("Category not found with id: " + categoryId);
        }

        CategoryEntity current = categoryOpt.get();

        // Costruisce il percorso dal basso verso l'alto
        while (current != null) {
            path.add(0, CategoryMapper.toResponse(current)); // Inserisce all'inizio
            current = current.getParentCategory();
        }

        return path;
    }

    /**
     * Ottiene tutti i discendenti di una categoria (ricorsivamente)
     */
    public List<CategoryResponseDto> getAllDescendants(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));

        List<CategoryResponseDto> descendants = new ArrayList<>();
        collectDescendants(category, descendants);

        return descendants;
    }

    /**
     * Raccoglie ricorsivamente tutti i discendenti
     */
    private void collectDescendants(CategoryEntity category, List<CategoryResponseDto> descendants) {
        for (CategoryEntity subCategory : category.getSubCategories()) {
            descendants.add(CategoryMapper.toResponse(subCategory));
            collectDescendants(subCategory, descendants); // Ricorsione
        }
    }

    /**
     * Ottiene la profondità di una categoria nella gerarchia
     */
    public int getCategoryDepth(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));

        int depth = 0;
        CategoryEntity current = category.getParentCategory();

        while (current != null) {
            depth++;
            current = current.getParentCategory();
        }

        return depth;
    }

    /**
     * Verifica se una categoria è antenata di un'altra
     */
    public boolean isAncestorOf(Long ancestorId, Long descendantId) {
        if (ancestorId.equals(descendantId)) {
            return false; // Una categoria non può essere antenata di se stessa
        }

        CategoryEntity descendant = categoryRepository.findById(descendantId)
                .orElseThrow(
                        () -> new CategoryNotFoundException("Descendant category not found with id: " + descendantId));

        CategoryEntity current = descendant.getParentCategory();
        while (current != null) {
            if (current.getId().equals(ancestorId)) {
                return true;
            }
            current = current.getParentCategory();
        }

        return false;
    }

    /**
     * Ottiene la categoria radice di una categoria
     */
    public CategoryResponseDto getRootCategory(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));

        CategoryEntity current = category;
        while (current.getParentCategory() != null) {
            current = current.getParentCategory();
        }

        return CategoryMapper.toResponse(current);
    }

    /**
     * Conta il numero totale di discendenti (ricorsivamente)
     */
    public long countAllDescendants(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));

        return countDescendantsRecursively(category);
    }

    private long countDescendantsRecursively(CategoryEntity category) {
        long count = category.getSubCategories().size();

        for (CategoryEntity subCategory : category.getSubCategories()) {
            count += countDescendantsRecursively(subCategory);
        }

        return count;
    }
}
