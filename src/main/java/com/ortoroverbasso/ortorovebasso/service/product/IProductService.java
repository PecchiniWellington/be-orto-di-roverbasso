package com.ortoroverbasso.ortorovebasso.service.product;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

public interface IProductService {

    ProductResponseDto createProduct(ProductRequestDto product);

    ProductEntity getProductById(Long productId);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductBySku(String sku);

}
