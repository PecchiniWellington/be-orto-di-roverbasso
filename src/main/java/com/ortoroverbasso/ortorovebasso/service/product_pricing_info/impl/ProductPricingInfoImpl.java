package com.ortoroverbasso.ortorovebasso.service.product_pricing_info.impl;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product_pricing_info.ProductPricingInfoRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product_pricing_info.ProductPricingInfoResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product_pricing_info.IProductPricingInfoService;

@Service
public class ProductPricingInfoImpl implements IProductPricingInfoService {

    @Override
    public ProductPricingInfoResponseDto getProductPricingInfoBySku(String sku) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductPricingInfoBySku'");
    }

    @Override
    public ProductPricingInfoResponseDto getProductPricingInfoById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductPricingInfoById'");
    }

    @Override
    public ProductPricingInfoResponseDto createProductPricingInfo(Long productId,
            ProductPricingInfoRequestDto productInfo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProductPricingInfo'");
    }

    @Override
    public ProductPricingInfoResponseDto updateProductPricingInfo(String id, ProductPricingInfoRequestDto productInfo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProductPricingInfo'");
    }

}
