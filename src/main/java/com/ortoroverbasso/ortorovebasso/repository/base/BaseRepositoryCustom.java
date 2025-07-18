package com.ortoroverbasso.ortorovebasso.repository.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repository base per operazioni custom comuni
 */
public interface BaseRepositoryCustom<T, FilterDto> {

    /**
     * Trova entità filtrate
     */
    Page<T> findFiltered(FilterDto filter, Pageable pageable);

    /**
     * Conta entità che corrispondono ai filtri
     */
    default long countFiltered(FilterDto filter) {
        // Implementazione di default che può essere sovrascritta
        return findFiltered(filter, Pageable.unpaged()).getTotalElements();
    }

    /**
     * Trova entità attive (se applicabile)
     */
    default List<T> findAllActive() {
        throw new UnsupportedOperationException("Method not implemented for this entity");
    }

    /**
     * Trova entità per campo testuale
     */
    default Page<T> searchByText(String searchTerm, Pageable pageable) {
        throw new UnsupportedOperationException("Method not implemented for this entity");
    }
}
