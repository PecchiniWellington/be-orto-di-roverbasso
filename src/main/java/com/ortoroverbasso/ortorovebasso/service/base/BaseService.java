package com.ortoroverbasso.ortorovebasso.service.base;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.paginate.PaginatedResponseDto;

/**
 * Service base generico per operazioni CRUD comuni
 */
public interface BaseService<ID, RequestDto, ResponseDto, FilterDto> {

    /**
     * Crea una nuova entità
     */
    @Transactional
    ResponseDto create(RequestDto dto);

    /**
     * Recupera tutte le entità con paginazione
     */
    @Transactional(readOnly = true)
    PaginatedResponseDto<ResponseDto> getAll(Pageable pageable);

    /**
     * Recupera un'entità per ID
     */
    @Transactional(readOnly = true)
    ResponseDto getById(ID id);

    /**
     * Aggiorna un'entità esistente
     */
    @Transactional
    ResponseDto update(ID id, RequestDto dto);

    /**
     * Elimina un'entità
     */
    @Transactional
    GenericResponseDto delete(ID id);

    /**
     * Recupera entità filtrate
     */
    @Transactional(readOnly = true)
    PaginatedResponseDto<ResponseDto> getFiltered(FilterDto filterDto, Pageable pageable);

    /**
     * Verifica se un'entità esiste
     */
    @Transactional(readOnly = true)
    boolean existsById(ID id);

    /**
     * Conta tutte le entità attive
     */
    @Transactional(readOnly = true)
    default long countActive() {
        throw new UnsupportedOperationException("Method not implemented for this entity");
    }
}
