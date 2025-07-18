package com.ortoroverbasso.ortorovebasso.mapper.manufacturer;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.manufacturer.ManufacturerEntity;
import com.ortoroverbasso.ortorovebasso.mapper.base.BaseMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;

@Component
public class ManufacturerMapper
        implements BaseMapper<ManufacturerEntity, ManufacturerRequestDto, ManufacturerResponseDto> {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ManufacturerEntity toEntity(ManufacturerRequestDto requestDto) {
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

    @Override
    public ManufacturerResponseDto toResponseDto(ManufacturerEntity entity) {
        if (entity == null) {
            return null;
        }

        ManufacturerResponseDto response = new ManufacturerResponseDto();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setUrlImage(entity.getUrlImage());
        response.setReference(entity.getReference());

        if (entity.getProducts() == null || entity.getProducts().isEmpty()) {
            response.setProducts(Collections.emptyList());
        } else {
            response.setProducts(productMapper.toResponseDtoList(entity.getProducts()));
        }

        return response;
    }

    @Override
    public void updateEntityFromDto(ManufacturerRequestDto dto, ManufacturerEntity entity) {
        if (dto == null || entity == null)
            return;

        entity.setName(dto.getName());
        entity.setUrlImage(dto.getUrlImage());
        entity.setReference(dto.getReference());
        // L'id non si aggiorna
    }
}
