package com.ortoroverbasso.ortorovebasso.mapper.product.product_large_quantities_price;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;

@Component
public class ProductLargeQuantityPriceMapper {

    public ProductLargeQuantityPriceEntity toEntity(ProductLargeQuantityPriceRequestDto dto) {
        if (dto == null)
            return null;

        ProductLargeQuantityPriceEntity entity = new ProductLargeQuantityPriceEntity();
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());

        ProductEntity productEntity = new ProductEntity();
        entity.setProduct(productEntity); // Placeholder, sarà sovrascritto nel service

        return entity;
    }

    public ProductLargeQuantityPriceResponseDto toResponseDto(ProductLargeQuantityPriceEntity entity) {
        if (entity == null)
            return null;

        ProductLargeQuantityPriceResponseDto response = new ProductLargeQuantityPriceResponseDto();
        response.setId(entity.getId());
        response.setQuantity(entity.getQuantity());
        response.setPrice(entity.getPrice());

        return response;
    }

    public List<ProductLargeQuantityPriceResponseDto> toResponseDtoList(
            List<ProductLargeQuantityPriceEntity> entities) {
        if (entities == null || entities.isEmpty())
            return List.of();

        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
