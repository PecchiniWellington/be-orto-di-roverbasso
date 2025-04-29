package com.ortoroverbasso.ortorovebasso.mapper.product.product_features;

import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_features.ProductFeaturesEntity;

public class ProductFeaturesMapper {

    public static ProductFeaturesResponseDto toResponseDto(ProductFeaturesEntity entity) {
        return new ProductFeaturesResponseDto(
                entity.getId(),
                entity.getFeature(),
                entity.getValue());
    }

    public static ProductFeaturesEntity toEntity(ProductFeaturesRequestDto dto) {

        return new ProductFeaturesEntity(
                dto.getId(),
                dto.getFeature(),
                dto.getValue());
    }

}
