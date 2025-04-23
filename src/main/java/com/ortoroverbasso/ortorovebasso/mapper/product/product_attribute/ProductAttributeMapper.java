package com.ortoroverbasso.ortorovebasso.mapper.product.product_attribute;

import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_attributes.ProductAttributesEntity;

@Component
public class ProductAttributeMapper {
    public static ProductAttributesEntity toEntity(ProductAttributesRequestDto dto) {
        ProductAttributesEntity entity = new ProductAttributesEntity();
        entity.setName(dto.getName());
        entity.setIsoCode(dto.getIsoCode());
        entity.setAttributeGroup(dto.getAttributeGroup());
        return entity;
    }

    // Metodo di istanza
    public static ProductAttributesResponseDto toResponseDto(ProductAttributesEntity entity) {
        ProductAttributesResponseDto dto = new ProductAttributesResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIsoCode(entity.getIsoCode());
        dto.setAttributeGroup(entity.getAttributeGroup());
        return dto;
    }

}
