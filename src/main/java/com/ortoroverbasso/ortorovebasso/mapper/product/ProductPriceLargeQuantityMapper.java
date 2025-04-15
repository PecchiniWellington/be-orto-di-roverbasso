package com.ortoroverbasso.ortorovebasso.mapper.product;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.product_price_large_quantity.ProductPriceLargeQuantityRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product_price_large_quantity.ProductPriceLargeQuantityResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductPriceLargeQuantityEntity;

public class ProductPriceLargeQuantityMapper {

    public static ProductPriceLargeQuantityEntity toEntity(ProductPriceLargeQuantityRequestDto dto) {
        ProductPriceLargeQuantityEntity product = new ProductPriceLargeQuantityEntity();
        product.setId(dto.getId());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setProduct(dto.getProduct());
        return product;
    }

    // Method to convert a single ProductPriceLargeQuantityEntity to
    // ProductPriceLargeQuantityResponseDto
    public static ProductPriceLargeQuantityResponseDto toResponseDto(ProductPriceLargeQuantityEntity product) {
        ProductPriceLargeQuantityResponseDto response = new ProductPriceLargeQuantityResponseDto();
        response.setId(product.getId());
        response.setQuantity(product.getQuantity());
        response.setPrice(product.getPrice());
        response.setProduct(product.getProduct());
        return response;
    }

    // Method to convert List<ProductLargeQuantityEntity> to
    // List<ProductLargeQuantityResponseDto>
    public static List<ProductPriceLargeQuantityResponseDto> toResponseDto(
            List<ProductPriceLargeQuantityEntity> products) {
        return products.stream()
                .map(ProductPriceLargeQuantityMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
