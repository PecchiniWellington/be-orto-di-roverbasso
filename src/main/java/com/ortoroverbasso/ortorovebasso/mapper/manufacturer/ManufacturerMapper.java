package com.ortoroverbasso.ortorovebasso.mapper.manufacturer;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.manufacturer.ManufacturerEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;

public class ManufacturerMapper {

    public static ManufacturerEntity toEntity(ManufacturerRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        ManufacturerEntity entity = new ManufacturerEntity();
        entity.setId(requestDto.getId());
        entity.setName(requestDto.getName());
        entity.setUrlImage(requestDto.getUrlImage());
        entity.setReference(requestDto.getReference());

        return entity;
    }

    public static ManufacturerResponseDto toResponseDto(ManufacturerEntity entity) {
        if (entity == null) {
            return null;
        }
        ManufacturerResponseDto response = new ManufacturerResponseDto();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setUrlImage(entity.getUrlImage());
        response.setReference(entity.getReference());

        if (entity.getProducts() == null) {
            response.setProducts(List.of());
        } else {
            response.setProducts(ProductMapper.toResponseListDto(entity.getProducts()));
        }
        return response;

    }

}
