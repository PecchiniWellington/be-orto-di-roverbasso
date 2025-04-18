package com.ortoroverbasso.ortorovebasso.mapper.shipping;

import com.ortoroverbasso.ortorovebasso.dto.shipping.ExcludedCategoryIdsRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.shipping.ExcludedCategoryIdsEntity;

public class ExcludedCategoryIdsMapper {

    public static ExcludedCategoryIdsEntity toEntity(ExcludedCategoryIdsRequestDto dto) {
        ExcludedCategoryIdsEntity entity = new ExcludedCategoryIdsEntity();
        entity.setCategoryIds(dto.getCategoryIds());
        return entity;
    }

    public static ExcludedCategoryIdsRequestDto toRequestDto(ExcludedCategoryIdsEntity entity) {
        ExcludedCategoryIdsRequestDto dto = new ExcludedCategoryIdsRequestDto();
        dto.setCategoryIds(entity.getCategoryIds());
        return dto;
    }
}
