package com.ortoroverbasso.ortorovebasso.service.product.product_features;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;

public interface IProductFeaturesService {
    ProductFeaturesResponseDto createProductFeatures(Long productId, ProductFeaturesRequestDto productFeatures);

    ProductFeaturesResponseDto updateProductFeatures(Long productId, ProductFeaturesRequestDto productFeatures,
            Long id);

    List<ProductFeaturesResponseDto> getAllProductFeatures(Long productId);

    void deleteProductFeatures(Long productId, Long featureId);

}
