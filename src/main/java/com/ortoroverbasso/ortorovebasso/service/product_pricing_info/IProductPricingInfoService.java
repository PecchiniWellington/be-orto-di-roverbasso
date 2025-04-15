package com.ortoroverbasso.ortorovebasso.service.product_pricing_info;

import com.ortoroverbasso.ortorovebasso.dto.product_pricing_info.ProductPricingInfoRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product_pricing_info.ProductPricingInfoResponseDto;

public interface IProductPricingInfoService {
    ProductPricingInfoResponseDto getProductPricingInfoBySku(String sku);

    ProductPricingInfoResponseDto getProductPricingInfoById(String id);

    ProductPricingInfoResponseDto createProductPricingInfo(Long productId, ProductPricingInfoRequestDto productInfo);

    ProductPricingInfoResponseDto updateProductPricingInfo(String id, ProductPricingInfoRequestDto productInfo);

}
