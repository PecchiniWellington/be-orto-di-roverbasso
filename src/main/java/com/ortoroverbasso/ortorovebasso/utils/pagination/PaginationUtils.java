package com.ortoroverbasso.ortorovebasso.utils.pagination;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ortoroverbasso.ortorovebasso.dto.filters.paginate.PaginatedResponseDto;

/**
 * Utility per gestione paginazione riutilizzabile in tutti i moduli
 */
public final class PaginationUtils {

    private PaginationUtils() {
        // Utility class
    }

    /**
     * Crea un Page da una lista e conteggio totale
     */
    public static <T> Page<T> createPage(List<T> content, Pageable pageable, long total) {
        return new PageImpl<>(content, pageable, total);
    }

    /**
     * Converte Page in PaginatedResponseDto
     */
    public static <T> PaginatedResponseDto<T> toPaginatedResponse(Page<T> page) {
        return PaginatedResponseDto.fromPage(page);
    }

    /**
     * Converte lista + pageable + total in PaginatedResponseDto
     */
    public static <T> PaginatedResponseDto<T> toPaginatedResponse(List<T> content, Pageable pageable, long total) {
        Page<T> page = createPage(content, pageable, total);
        return PaginatedResponseDto.fromPage(page);
    }

    /**
     * Crea Pageable con sorting sicuro
     */
    public static Pageable createPageable(int page, int size, Sort sort) {
        // Validation
        page = Math.max(0, page);
        size = Math.min(Math.max(1, size), 100); // Min 1, Max 100

        if (sort == null) {
            sort = Sort.by(Sort.Direction.ASC, "id");
        }

        return PageRequest.of(page, size, sort);
    }

    /**
     * Crea Pageable semplice con validazione
     */
    public static Pageable createPageable(int page, int size) {
        return createPageable(page, size, null);
    }

    /**
     * Applica sorting dinamico
     */
    public static Pageable applySorting(Pageable pageable, String sortField, String sortDirection) {
        if (sortField == null || sortField.isEmpty()) {
            return pageable;
        }

        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDirection) || "DESC".equals(sortDirection)) {
            direction = Sort.Direction.DESC;
        }

        Sort sort = Sort.by(direction, sortField);
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }
}
