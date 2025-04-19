package com.ortoroverbasso.ortorovebasso.mapper.shipping;

import com.ortoroverbasso.ortorovebasso.dto.shipping.CarrierRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.CarrierResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.shipping.CarriersEntity;

public class CarrierMapper {

    // Mapping from CarrierRequestDto to CarrierEntity
    public static CarriersEntity toEntity(CarrierRequestDto dto) {
        CarriersEntity entity = new CarriersEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    // Mapping from CarriersEntity to CarrierResponseDto
    public static CarrierResponseDto toResponse(CarriersEntity entity) {
        CarrierResponseDto dto = new CarrierResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}
