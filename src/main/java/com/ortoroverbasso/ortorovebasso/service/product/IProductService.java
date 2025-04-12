package com.ortoroverbasso.ortorovebasso.service.product;

import com.ortoroverbasso.ortorovebasso.dto.Product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.Product.ProductResponseDto;

public interface IProductService {

    ProductResponseDto createProduct(ProductRequestDto product);

    // Qui aggiungerò altri metodi come updateProduct(), getProductById(), ecc.
}
