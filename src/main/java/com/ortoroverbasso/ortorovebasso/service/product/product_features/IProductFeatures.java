package com.ortoroverbasso.ortorovebasso.service.product.product_features;

import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;

public interface IProductFeatures {
    ProductFeaturesResponseDto createProductFeatures(Long productId, ProductFeaturesRequestDto productFeatures);

    ProductFeaturesResponseDto updateProductFeatures(Long productId, ProductFeaturesRequestDto productFeatures);

    ProductFeaturesResponseDto getAllProductFeatures(Long productId);

    void deleteProductFeatures(Long productId, String feature);

}
