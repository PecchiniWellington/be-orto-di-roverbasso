package com.ortoroverbasso.ortorovebasso.service.product.product_filters;

import org.springframework.data.domain.Pageable;

import com.ortoroverbasso.ortorovebasso.dto.filters.paginate.PaginatedResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFacetResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;

/**
 * Interfaccia per il servizio di gestione dei filtri e facet dei prodotti
 */
public interface IProductFacetService {

    /**
     * Recupera i filtri disponibili (facet) per i prodotti
     *
     * @param filterDto Criteri di filtro attuali
     * @return ProductFacetResponseDto contenente i filtri disponibili
     */
    ProductFacetResponseDto getFacets(ProductFilterRequestDto filterDto);

    /**
     * Recupera prodotti filtrati secondo criteri specifici
     *
     * @param filterDto Criteri di filtro
     * @param pageable  Parametri di paginazione
     * @return PaginatedResponseDto contenente i prodotti filtrati
     */
    PaginatedResponseDto<ProductResponseDto> getFilteredProducts(
            ProductFilterRequestDto filterDto, Pageable pageable);
}
