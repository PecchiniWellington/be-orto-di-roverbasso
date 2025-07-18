package com.ortoroverbasso.ortorovebasso.mapper.base;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper base generico riutilizzabile per tutti i moduli
 */
public interface BaseMapper<Entity, RequestDto, ResponseDto> {

    /**
     * Converte RequestDto in Entity
     */
    Entity toEntity(RequestDto dto);

    /**
     * Converte Entity in ResponseDto
     */
    ResponseDto toResponseDto(Entity entity);

    /**
     * Aggiorna Entity esistente con dati da RequestDto
     */
    void updateEntityFromDto(RequestDto dto, Entity entity);

    /**
     * Converte lista di Entity in lista di ResponseDto
     */
    default List<ResponseDto> toResponseDtoList(List<Entity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Converte lista di RequestDto in lista di Entity
     */
    default List<Entity> toEntityList(List<RequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return Collections.emptyList();
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
