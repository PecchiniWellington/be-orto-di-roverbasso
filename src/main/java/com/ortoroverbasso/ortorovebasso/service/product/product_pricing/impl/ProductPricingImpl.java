package com.ortoroverbasso.ortorovebasso.service.product.product_pricing.impl;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_pricing.IProductPricingService;

@Service
public class ProductPricingImpl implements IProductPricingService {

    public ProductPricingImpl() {
        // Constructor logic if needed
    }

    @Override
    public ProductPricingResponseDto getProductPricingBySku(String sku) {
        throw new UnsupportedOperationException("Unimplemented method 'getProductPricingBySku'");
    }

    @Override
    public ProductPricingResponseDto getProductPricingById(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'getProductPricingById'");
    }

    @Override
    public ProductPricingResponseDto updateProductPricing(String id, ProductPricingRequestDto product) {
        throw new UnsupportedOperationException("Unimplemented method 'updateProductPricing'");
    }

}
