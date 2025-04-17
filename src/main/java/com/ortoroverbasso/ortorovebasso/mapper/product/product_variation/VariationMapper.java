package com.ortoroverbasso.ortorovebasso.mapper.product.product_variation;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.VariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.VariationResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_variation.VariationEntity;

public class VariationMapper {

    public static VariationResponseDto toResponse(VariationEntity entity) {
        VariationResponseDto dto = new VariationResponseDto();
        dto.setId(entity.getId());

        List<ProductAttributesResponseDto> attributeDtos = entity.getAttributes().stream()
                .map(attribute -> {
                    ProductAttributesResponseDto attributeDto = new ProductAttributesResponseDto();
                    attributeDto.setId(attribute.getId());
                    attributeDto.setName(attribute.getName());
                    attributeDto.setIsoCode(attribute.getIsoCode());
                    return attributeDto;
                }).collect(Collectors.toList());

        dto.setAttributes(attributeDtos);
        return dto;
    }

    public static VariationEntity toEntity(VariationRequestDto dto) {
        VariationEntity entity = new VariationEntity();
        entity.setId(dto.getId());
        return entity;
    }
}
