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

        // Recupera le sottocategorie della categoria
        Set<CategoryResponseDto> subCategoriesDtos = category.getSubCategories().stream()
                .map(subCategory -> new CategoryResponseDto(subCategory.getId(), subCategory.getName()))
                .collect(Collectors.toSet());

        // Mappa la categoria, includendo le sottocategorie
        CategoryResponseDto categoryResponseDto = CategoryMapper.toResponse(category);
        categoryResponseDto.setSubCategories(subCategoriesDtos);

        return categoryResponseDto;
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();

        return categories.stream()
                .filter(category -> category.getParentCategory() == null) // Solo le categorie principali
                .map(category -> {
                    // Mappa i prodotti della categoria
                    Set<ProductCategoryResponseDto> productIdAndNameDtos = category.getProducts().stream()
                            .map(product -> {
                                String productName = (product.getProductInformation() != null)
                                        ? product.getProductInformation().getName()
                                        : "";
                                return new ProductCategoryResponseDto(product.getId(), productName);
                            })
                            .collect(Collectors.toSet());

                    // Mappa le sottocategorie, includendo i prodotti se presenti
                    Set<CategoryResponseDto> subCategoriesDtos = getSubCategoriesRecursive(category);

                    // Crea la risposta per la categoria principale
                    CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
                    categoryResponseDto.setId(category.getId());
                    categoryResponseDto.setName(category.getName());
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
                .map(subCategory -> {
                    Set<ProductCategoryResponseDto> subCategoryProducts = subCategory.getProducts().stream()
                            .map(product -> new ProductCategoryResponseDto(product.getId(),
                                    product.getProductInformation() != null ? product.getProductInformation().getName()
                                            : ""))
                            .collect(Collectors.toSet());

                    // Mappa la subcategoria ricorsivamente
                    Set<CategoryResponseDto> nestedSubCategories = getSubCategoriesRecursive(subCategory);

                    CategoryResponseDto subCategoryDto = new CategoryResponseDto();
                    subCategoryDto.setId(subCategory.getId());
                    subCategoryDto.setName(subCategory.getName());
                    subCategoryDto.setProducts(subCategoryProducts);
                    subCategoryDto.setSubCategories(nestedSubCategories); // Aggiungi le subcategorie annidate
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
            System.out.println("Parent category found: " + parentCategory.getName());

            if (!parentCategory.getSubCategories().contains(category)) {
                // Aggiungi questa sottocategoria alla lista delle sottocategorie della
                // categoria principale
                parentCategory.getSubCategories().add(category);
                System.out.println("Sottocategoria aggiunta alla categoria principale.");
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
        // Recupera la categoria da eliminare
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Ottieni o crea la categoria fittizia "Nessuna categoria associata"
        CategoryEntity noCategory = categoryRepository.findByName("Nessuna categoria associata")
                .orElseGet(() -> {
                    CategoryEntity newCategory = new CategoryEntity();
                    newCategory.setName("Nessuna categoria associata");
                    return categoryRepository.save(newCategory); // Salva la categoria fittizia
                });

        // Disassociare tutti i prodotti dalla categoria e associarli a "Nessuna
        // categoria associata"
        category.getProducts().forEach(product -> {
            if (product.getCategory() != null) {
                // Impostiamo il prodotto nella categoria "Nessuna categoria associata"
                product.setCategory(noCategory);

                // Se il prodotto ha associato "product_information", possiamo gestirlo
                if (product.getProductInformation() != null) {
                    // Settiamo la relazione null per "product_information" se è legata alla
                    // categoria eliminata
                    product.getProductInformation().setProduct(null); // Disassocia il prodotto da product_information
                }

                productRepository.save(product); // Aggiorniamo il prodotto nel database
            }
        });

        // Disassociare tutte le sottocategorie ricorsivamente
        deleteSubCategories(category, noCategory);

        // Procedi con la cancellazione della categoria principale
        categoryRepository.delete(category); // Ora possiamo eliminare la categoria
    }

    // Metodo ricorsivo per eliminare le sottocategorie e disassociare i loro
    // prodotti
    private void deleteSubCategories(CategoryEntity category, CategoryEntity noCategory) {
        category.getSubCategories().forEach(subCategory -> {
            // Disassociare i prodotti della sottocategoria e associarli a "Nessuna
            // categoria associata"
            subCategory.getProducts().forEach(product -> {
                if (product.getCategory() != null) {
                    product.setCategory(noCategory); // Disassociare il prodotto dalla sottocategoria

                    // Se il prodotto ha associato "product_information", possiamo gestirlo
                    if (product.getProductInformation() != null) {
                        // Settiamo la relazione null per "product_information" se è legata alla
                        // sottocategoria eliminata
                        product.getProductInformation().setProduct(null); // Disassocia il prodotto da
                                                                          // product_information
                    }

                    productRepository.save(product); // Aggiorna il prodotto nel database
                }
            });

            // Disassociare ulteriori sottocategorie, se esistono
            deleteSubCategories(subCategory, noCategory); // Ricorsione per sottocategorie
            categoryRepository.delete(subCategory); // Elimina la sottocategoria
        });
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
