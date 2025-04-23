package com.ortoroverbasso.ortorovebasso.mapper.product.product_attribute;

import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributeGroupDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_attributes.ProductAttributeGroupsEntity;

@Component
public class ProductAttributeGroupsMapper {

    // Metodo di istanza, non statico
    public static ProductAttributeGroupsEntity toEntity(ProductAttributeGroupDto dto) {
        ProductAttributeGroupsEntity entity = new ProductAttributeGroupsEntity();
        entity.setName(dto.getName());
        entity.setIsoCode(dto.getIsoCode());
        return entity;
    }

    // Metodo di istanza
    public static ProductAttributeGroupDto toDto(ProductAttributeGroupsEntity entity) {
        ProductAttributeGroupDto dto = new ProductAttributeGroupDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIsoCode(entity.getIsoCode());
        return dto;
    }
}
