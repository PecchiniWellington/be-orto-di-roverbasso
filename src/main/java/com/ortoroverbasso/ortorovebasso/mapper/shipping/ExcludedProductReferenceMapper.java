package com.ortoroverbasso.ortorovebasso.mapper.shipping;

import com.ortoroverbasso.ortorovebasso.dto.shipping.ExcludedProductReferenceRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ExcludedProductReferenceEntity;

public class ExcludedProductReferenceMapper {

    public static ExcludedProductReferenceEntity toEntity(ExcludedProductReferenceRequestDto dto) {
        ExcludedProductReferenceEntity entity = new ExcludedProductReferenceEntity();
        entity.setReference(dto.getReference());
        return entity;
    }

    public static ExcludedProductReferenceRequestDto toRequestDto(ExcludedProductReferenceEntity entity) {
        ExcludedProductReferenceRequestDto dto = new ExcludedProductReferenceRequestDto();
        dto.setReference(entity.getReference());
        return dto;
    }
}
