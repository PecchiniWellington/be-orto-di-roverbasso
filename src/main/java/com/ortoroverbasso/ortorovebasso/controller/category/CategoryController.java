package com.ortoroverbasso.ortorovebasso.controller.category;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.category.CategoryRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.category.CategoryResponseDto;
import com.ortoroverbasso.ortorovebasso.service.category.CategoryHierarchyService;
import com.ortoroverbasso.ortorovebasso.service.category.ICategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private CategoryHierarchyService hierarchyService;

    /**
     * Recupera una categoria per ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable Long id) {
        CategoryResponseDto category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

    /**
     * Recupera una categoria per slug
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<CategoryResponseDto> getCategoryBySlug(@PathVariable String slug) {
        CategoryResponseDto category = categoryService.getCategoryBySlug(slug);
        return ResponseEntity.ok(category);
    }

    /**
     * Recupera tutte le categorie con gerarchia completa
     */
    @GetMapping
    public ResponseEntity<Set<CategoryResponseDto>> getAllCategories(
            @RequestParam(defaultValue = "false") boolean rootOnly) {

        Set<CategoryResponseDto> categories = rootOnly
                ? categoryService.getRootCategories()
                : categoryService.getAllCategories();

        return ResponseEntity.ok(categories);
    }

    /**
     * Recupera le sottocategorie di una categoria specifica
     */
    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<Set<CategoryResponseDto>> getSubCategories(@PathVariable Long parentId) {
        Set<CategoryResponseDto> subCategories = categoryService.getSubCategories(parentId);
        return ResponseEntity.ok(subCategories);
    }

    /**
     * Crea una nuova categoria
     */
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto createdCategory = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    /**
     * Aggiorna una categoria esistente
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto updatedCategory = categoryService.updateCategory(id, categoryRequestDto);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Crea una sottocategoria
     */
    @PostMapping("/{parentId}/subcategories")
    public ResponseEntity<CategoryResponseDto> createSubCategory(
            @PathVariable Long parentId,
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto subCategory = categoryService.createSubCategory(parentId, categoryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategory);
    }

    /**
     * Elimina una categoria
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Verifica se una categoria ha sottocategorie
     */
    @GetMapping("/{id}/has-subcategories")
    public ResponseEntity<Boolean> hasSubCategories(@PathVariable Long id) {
        boolean hasSubCategories = categoryService.hasSubCategories(id);
        return ResponseEntity.ok(hasSubCategories);
    }

    /**
     * Verifica se una categoria ha prodotti
     */
    @GetMapping("/{id}/has-products")
    public ResponseEntity<Boolean> hasProducts(@PathVariable Long id) {
        boolean hasProducts = categoryService.hasProducts(id);
        return ResponseEntity.ok(hasProducts);
    }

    /**
     * Ottiene il percorso completo (breadcrumb) di una categoria
     */
    @GetMapping("/{id}/path")
    public ResponseEntity<List<CategoryResponseDto>> getCategoryPath(@PathVariable Long id) {
        List<CategoryResponseDto> path = hierarchyService.getCategoryPath(id);
        return ResponseEntity.ok(path);
    }

    /**
     * Ottiene tutti i discendenti di una categoria
     */
    @GetMapping("/{id}/descendants")
    public ResponseEntity<List<CategoryResponseDto>> getAllDescendants(@PathVariable Long id) {
        List<CategoryResponseDto> descendants = hierarchyService.getAllDescendants(id);
        return ResponseEntity.ok(descendants);
    }

    /**
     * Ottiene la profondità di una categoria nella gerarchia
     */
    @GetMapping("/{id}/depth")
    public ResponseEntity<Integer> getCategoryDepth(@PathVariable Long id) {
        int depth = hierarchyService.getCategoryDepth(id);
        return ResponseEntity.ok(depth);
    }

    /**
     * Verifica se una categoria è antenata di un'altra
     */
    @GetMapping("/{ancestorId}/is-ancestor-of/{descendantId}")
    public ResponseEntity<Boolean> isAncestorOf(@PathVariable Long ancestorId, @PathVariable Long descendantId) {
        boolean isAncestor = hierarchyService.isAncestorOf(ancestorId, descendantId);
        return ResponseEntity.ok(isAncestor);
    }

    /**
     * Ottiene la categoria radice di una categoria
     */
    @GetMapping("/{id}/root")
    public ResponseEntity<CategoryResponseDto> getRootCategory(@PathVariable Long id) {
        CategoryResponseDto root = hierarchyService.getRootCategory(id);
        return ResponseEntity.ok(root);
    }
}
