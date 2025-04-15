package com.ortoroverbasso.ortorovebasso.mapper.product;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

public class ProductMapper {

    public static ProductEntity toEntity(ProductRequestDto dto) {
        ProductEntity product = new ProductEntity();
        product.setSku(dto.getSku());
        product.setWeight(dto.getWeight());
        product.setCategory(dto.getCategory());
        product.setRetailPrice(dto.getRetailPrice());
        product.setActive(dto.getActive() ? 1 : 0);
        return product;
    }

    // Method to convert ProductEntity to ProductResponseDto
    public static ProductResponseDto toResponseDto(ProductEntity product) {

        return new ProductResponseDto(
                product.getId(),
                product.getSku(),
                product.getRetailPrice(),
                product.getCategory(),
                product.getWeight(),
                product.getActive() == 1);
    }

    // Method to convert List<ProductEntity> to List<ProductResponseDto>
    public static List<ProductResponseDto> toResponseDto(List<ProductEntity> products) {
        return products.stream()
                .map(ProductMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
