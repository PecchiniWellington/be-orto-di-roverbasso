package com.ortoroverbasso.ortorovebasso.service.product;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;

public interface IProductService {

    ProductResponseDto createProduct(ProductRequestDto product);

    ProductResponseDto getProductById(Long productId);

    List<ProductResponseDto> getAllProducts();

    GenericResponseDto deleteProduct(Long productId);

}
