package com.ortoroverbasso.ortorovebasso.mapper.manufacturer;

import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.manufacturer.ManufacturerEntity;

public class ManufacturerMapper {

    public static ManufacturerEntity toEntity(ManufacturerRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        ManufacturerEntity entity = new ManufacturerEntity();
        entity.setName(requestDto.getName());
        entity.setUrlImage(requestDto.getUrlImage());
        entity.setReference(requestDto.getReference());
        return entity;
    }

    public static ManufacturerResponseDto toResponse(ManufacturerEntity entity) {
        if (entity == null) {
            return null;
        }
        ManufacturerResponseDto response = new ManufacturerResponseDto();
        response.setName(entity.getName());
        response.setUrlImage(entity.getUrlImage());
        response.setReference(entity.getReference());
        return response;

    }

}
