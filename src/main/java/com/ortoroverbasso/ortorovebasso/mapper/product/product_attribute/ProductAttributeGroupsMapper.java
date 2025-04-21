package com.ortoroverbasso.ortorovebasso.mapper.product.product_attribute;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributeGroupDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_attributes.ProductAttributeGroupsEntity;

public class ProductAttributeGroupsMapper {

    // Metodo di istanza, non statico
    public ProductAttributeGroupsEntity toEntity(ProductAttributeGroupDto dto) {
        ProductAttributeGroupsEntity entity = new ProductAttributeGroupsEntity();
        entity.setName(dto.getName());
        entity.setIsoCode(dto.getIsoCode());
        return entity;
    }

    // Metodo di istanza
    public ProductAttributeGroupDto toDto(ProductAttributeGroupsEntity entity) {
        ProductAttributeGroupDto dto = new ProductAttributeGroupDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIsoCode(entity.getIsoCode());
        return dto;
    }
}
