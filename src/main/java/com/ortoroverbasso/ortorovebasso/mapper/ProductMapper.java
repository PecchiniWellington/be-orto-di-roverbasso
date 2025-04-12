package com.ortoroverbasso.ortorovebasso.mapper;

import com.ortoroverbasso.ortorovebasso.dto.Product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.Product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setSku(dto.getSku());
        product.setWeight(dto.getWeight());
        product.setCategory(dto.getCategory());
        product.setRetailPrice(dto.getRetailPrice().toString());
        product.setActive(dto.getActive() ? 1 : 0);
        return product;
    }

    public static ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getSku(),
                product.getRetailPrice(),
                product.getCategory(),
                product.getWeight(),
                product.getActive() == 1);
    }
}
