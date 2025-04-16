package com.ortoroverbasso.ortorovebasso.service.product;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

public interface IProductService {

    ProductResponseDto createProduct(ProductRequestDto product);

    List<ProductInformationResponseDto> getProductPrices(Long productId);

    ProductResponseDto createProductPriceInfo(Long productId, ProductPricingRequestDto productPriceInfo);

    ProductEntity getProductById(Long productId);

    List<ProductResponseDto> getAllProducts();

}
