package com.ortoroverbasso.ortorovebasso.mapper.product;

import com.ortoroverbasso.ortorovebasso.dto.product_pricing_info.ProductPricingInfoResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductPricingInfoEntity;

public class ProductPricingInfoMapper {
    public ProductPricingInfoEntity toEntity(ProductPricingInfoResponseDto dto) {
        if (dto == null) {
            return null;
        }
        ProductPricingInfoEntity entity = new ProductPricingInfoEntity();
        entity.setId(dto.getId());
        entity.setSku(dto.getSku());
        entity.setWholesalePrice(dto.getWholesalePrice());
        entity.setRetailPrice(dto.getRetailPrice());
        entity.setInShopsPrice(dto.getInShopsPrice());

        return entity;
    }

    public static ProductPricingInfoResponseDto toResponseDto(ProductPricingInfoEntity entity) {
        if (entity == null) {
            return null;
        }
        ProductPricingInfoResponseDto dto = new ProductPricingInfoResponseDto();
        dto.setId(entity.getId());
        dto.setSku(entity.getSku());
        dto.setWholesalePrice(entity.getWholesalePrice());
        dto.setRetailPrice(entity.getRetailPrice());
        dto.setInShopsPrice(entity.getInShopsPrice());

        return dto;
    }
}
