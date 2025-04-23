package com.ortoroverbasso.ortorovebasso.mapper.product.product_variation;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_variation.ProductVariationEntity;

public class ProductVariationMapper {

    public static ProductVariationEntity toEntity(ProductVariationRequestDto dto) {
        ProductVariationEntity entity = new ProductVariationEntity();
        entity.setId(dto.getId());
        entity.setSku(dto.getSku());
        entity.setEan13(dto.getEan13());
        entity.setExtraWeight(dto.getExtraWeight());
        entity.setWholesalePrice(dto.getWholesalePrice());
        entity.setRetailPrice(dto.getRetailPrice());
        entity.setInShopsPrice(dto.getInShopsPrice());
        entity.setWidth(dto.getWidth());
        entity.setHeight(dto.getHeight());
        entity.setDepth(dto.getDepth());
        entity.setLogisticClass(dto.getLogisticClass());
        entity.setPartNumber(dto.getPartNumber());
        entity.setCanon(dto.getCanon());

        List<ProductLargeQuantityPriceEntity> prices = dto.getPriceLargeQuantities().stream()
                .map(priceDto -> new ProductLargeQuantityPriceEntity(priceDto.getQuantity(), priceDto.getPrice()))
                .collect(Collectors.toList());
        entity.setPriceLargeQuantities(prices);

        return entity;
    }

    public static ProductVariationResponseDto toResponse(ProductVariationEntity entity) {
        ProductVariationResponseDto dto = new ProductVariationResponseDto();
        dto.setId(entity.getId());
        dto.setSku(entity.getSku());
        dto.setEan13(entity.getEan13());
        dto.setExtraWeight(entity.getExtraWeight());
        dto.setWholesalePrice(entity.getWholesalePrice());
        dto.setRetailPrice(entity.getRetailPrice());
        dto.setInShopsPrice(entity.getInShopsPrice());
        dto.setWidth(entity.getWidth());
        dto.setHeight(entity.getHeight());
        dto.setDepth(entity.getDepth());

        if (entity.getProduct() != null) {
            dto.setProductId(entity.getProduct().getId());
        }

        // Mappa la lista dei prezzi
        List<ProductLargeQuantityPriceResponseDto> priceDtos = entity.getPriceLargeQuantities().stream()
                .map(priceEntity -> new ProductLargeQuantityPriceResponseDto(
                        priceEntity.getId(),
                        priceEntity.getQuantity(),
                        priceEntity.getPrice()))
                .collect(Collectors.toList());

        dto.setPriceLargeQuantities(priceDtos);

        dto.setLogisticClass(entity.getLogisticClass());
        dto.setPartNumber(entity.getPartNumber());
        dto.setCanon(entity.getCanon());

        return dto;
    }
}
