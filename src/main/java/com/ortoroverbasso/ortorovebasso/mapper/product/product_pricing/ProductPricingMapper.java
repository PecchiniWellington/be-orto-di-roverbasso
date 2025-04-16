package com.ortoroverbasso.ortorovebasso.mapper.product.product_pricing;

import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_pricing.ProductPricingEntity;

@Component
public class ProductPricingMapper {
    public ProductPricingEntity toEntity(ProductPricingResponseDto dto) {
        if (dto == null) {
            return null;
        }
        ProductPricingEntity entity = new ProductPricingEntity();
        entity.setId(dto.getId());
        entity.setSku(dto.getSku());
        entity.setWholesalePrice(dto.getWholesalePrice());
        entity.setRetailPrice(dto.getRetailPrice());
        entity.setInShopsPrice(dto.getInShopsPrice());

        return entity;
    }

    public static ProductPricingResponseDto toResponseDto(ProductPricingEntity entity) {
        if (entity == null) {
            return null;
        }
        ProductPricingResponseDto dto = new ProductPricingResponseDto();
        dto.setId(entity.getId());
        dto.setSku(entity.getSku());
        dto.setWholesalePrice(entity.getWholesalePrice());
        dto.setRetailPrice(entity.getRetailPrice());
        dto.setInShopsPrice(entity.getInShopsPrice());

        return dto;
    }
}
