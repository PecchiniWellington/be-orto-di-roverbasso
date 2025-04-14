package com.ortoroverbasso.ortorovebasso.service.product;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.Product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.Product.ProductResponseDto;

public interface IProductService {

    ProductResponseDto createProduct(ProductRequestDto product);

    List<ProductResponseDto> getAllProducts();

}
