package com.ortoroverbasso.ortorovebasso.service.product;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingRequestDto;

public interface IProductService {

    ProductResponseDto createProduct(ProductRequestDto product);

    List<ProductInformationResponseDto> getProductPrices(Long productId);

    ProductResponseDto createProductPriceInfo(Long productId, ProductPricingRequestDto productPriceInfo);

    ProductResponseDto getProductById(Long productId);

    List<ProductResponseDto> getAllProducts();

    GenericResponseDto deleteProduct(Long productId);

}
