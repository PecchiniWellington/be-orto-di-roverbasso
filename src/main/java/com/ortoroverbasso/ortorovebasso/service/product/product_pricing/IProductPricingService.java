package com.ortoroverbasso.ortorovebasso.service.product.product_pricing;

import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingResponseDto;

public interface IProductPricingService {
    ProductPricingResponseDto getProductPricingBySku(String sku);

    ProductPricingResponseDto getProductPricingById(String id);

    ProductPricingResponseDto updateProductPricing(String id, ProductPricingRequestDto productInfo);

}
