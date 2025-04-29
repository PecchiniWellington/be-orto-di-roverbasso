package com.ortoroverbasso.ortorovebasso.mapper.product.product_large_quantities_price;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;

public class ProductLargeQuantityPriceMapper {

    public static ProductLargeQuantityPriceEntity toEntity(ProductLargeQuantityPriceRequestDto dto) {
        ProductLargeQuantityPriceEntity product = new ProductLargeQuantityPriceEntity();
        product.setId(dto.getId());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());

        ProductEntity productEntity = new ProductEntity();

        product.setProduct(productEntity);

        return product;
    }

    public static ProductLargeQuantityPriceResponseDto toResponseDto(ProductLargeQuantityPriceEntity product) {
        ProductLargeQuantityPriceResponseDto response = new ProductLargeQuantityPriceResponseDto();
        response.setId(product.getId());
        response.setQuantity(product.getQuantity());
        response.setPrice(product.getPrice());

        return response;
    }

    public static List<ProductLargeQuantityPriceResponseDto> toResponseDto(
            List<ProductLargeQuantityPriceEntity> products) {
        return products.stream()
                .map(ProductLargeQuantityPriceMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
