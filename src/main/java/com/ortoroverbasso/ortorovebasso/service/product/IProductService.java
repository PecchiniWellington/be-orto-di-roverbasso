package com.ortoroverbasso.ortorovebasso.service.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.paginate.PaginatedResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFacetResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductFlatDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.exception.ProductAlreadyExistsException;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;

/**
 * Interfaccia del servizio per la gestione dei prodotti
 */
public interface IProductService {

    /**
     * Crea un nuovo prodotto
     *
     * @param dto Dati del prodotto da creare
     * @return ProductResponseDto del prodotto creato
     * @throws ProductAlreadyExistsException se il SKU esiste già
     * @throws CategoryNotFoundException     se la categoria non esiste
     */
    ProductResponseDto createProduct(ProductRequestDto dto);

    /**
     * Recupera tutti i prodotti con paginazione
     *
     * @param pageable Parametri di paginazione
     * @return PaginatedResponseDto contenente i prodotti
     */
    PaginatedResponseDto<ProductResponseDto> getAllProducts(Pageable pageable);

    /**
     * Recupera un prodotto per ID
     *
     * @param productId ID del prodotto
     * @return ProductResponseDto del prodotto trovato
     * @throws ProductNotFoundException se il prodotto non esiste
     */
    ProductResponseDto getProductById(Long productId);

    /**
     * Aggiorna un prodotto esistente
     *
     * @param productId ID del prodotto da aggiornare
     * @param dto       Nuovi dati del prodotto
     * @return ProductResponseDto del prodotto aggiornato
     * @throws ProductNotFoundException  se il prodotto non esiste
     * @throws CategoryNotFoundException se la categoria non esiste
     */
    ProductResponseDto updateProduct(Long productId, ProductRequestDto dto);

    /**
     * Elimina un prodotto
     *
     * @param productId ID del prodotto da eliminare
     * @return GenericResponseDto con il risultato dell'operazione
     * @throws ProductNotFoundException se il prodotto non esiste
     */
    GenericResponseDto deleteProduct(Long productId);

    /**
     * Recupera tutti i prodotti di una categoria specifica
     *
     * @param categoryId ID della categoria
     * @return Lista dei prodotti della categoria
     * @throws CategoryNotFoundException se la categoria non esiste
     */
    List<ProductResponseDto> getProductsByCategory(Long categoryId);

    /**
     * Recupera tutti i prodotti di una categoria e delle sue sottocategorie
     *
     * @param slug Slug della categoria
     * @return Lista dei prodotti della categoria e sottocategorie
     * @throws CategoryNotFoundException se la categoria non esiste
     */
    List<ProductResponseDto> getProductsByCategorySlug(String slug);

    /**
     * Recupera tutti i prodotti di una sottocategoria specifica
     *
     * @param slug Slug della sottocategoria
     * @return Lista dei prodotti della sottocategoria
     * @throws CategoryNotFoundException se la sottocategoria non esiste
     */
    List<ProductResponseDto> getProductsBySubCategorySlug(String slug);

    /**
     * Recupera prodotti filtrati secondo criteri specifici
     *
     * @param filterDto Criteri di filtro
     * @param pageable  Parametri di paginazione
     * @return PaginatedResponseDto contenente i prodotti filtrati
     */
    PaginatedResponseDto<ProductResponseDto> getFilteredProducts(
            ProductFilterRequestDto filterDto, Pageable pageable);

    /**
     * Recupera i filtri disponibili per i prodotti
     *
     * @param filterDto Criteri di filtro attuali
     * @return ProductFacetResponseDto contenente i filtri disponibili
     */
    ProductFacetResponseDto getAvailableFilters(ProductFilterRequestDto filterDto);

    /**
     * Recupera una lista semplificata di tutti i prodotti (non paginata)
     *
     * @return Lista di ProductFlatDto
     */
    List<ProductFlatDto> getFlatProducts();

    /**
     * Recupera una lista semplificata di prodotti con paginazione
     *
     * @param pageable Parametri di paginazione
     * @return Page di ProductFlatDto
     */
    Page<ProductFlatDto> getFlatProductsPaginated(Pageable pageable);

    /**
     * Verifica se un prodotto esiste
     *
     * @param productId ID del prodotto
     * @return true se il prodotto esiste, false altrimenti
     */
    boolean existsById(Long productId);

    /**
     * Verifica se un SKU esiste già
     *
     * @param sku SKU da verificare
     * @return true se il SKU esiste, false altrimenti
     */
    boolean existsBySku(String sku);

    /**
     * Conta il numero totale di prodotti attivi
     *
     * @return Numero di prodotti attivi
     */
    long countActiveProducts();

    /**
     * Recupera prodotti per range di prezzo
     *
     * @param minPrice Prezzo minimo
     * @param maxPrice Prezzo massimo
     * @param pageable Parametri di paginazione
     * @return PaginatedResponseDto contenente i prodotti nel range di prezzo
     */
    PaginatedResponseDto<ProductResponseDto> getProductsByPriceRange(
            Double minPrice, Double maxPrice, Pageable pageable);

    /**
     * Aggiorna lo stato attivo/inattivo di un prodotto
     *
     * @param productId ID del prodotto
     * @param active    Nuovo stato attivo
     * @return ProductResponseDto del prodotto aggiornato
     * @throws ProductNotFoundException se il prodotto non esiste
     */
    ProductResponseDto updateProductStatus(Long productId, Boolean active);

    /**
     * Aggiorna la quantità di un prodotto
     *
     * @param productId ID del prodotto
     * @param quantity  Nuova quantità
     * @return ProductResponseDto del prodotto aggiornato
     * @throws ProductNotFoundException se il prodotto non esiste
     */
    ProductResponseDto updateProductQuantity(Long productId, Integer quantity);
}
