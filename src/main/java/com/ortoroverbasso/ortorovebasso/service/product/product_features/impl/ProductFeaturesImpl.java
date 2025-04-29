package com.ortoroverbasso.ortorovebasso.service.product.product_features.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductConnectFeaturesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_features.ProductFeaturesEntity;
import com.ortoroverbasso.ortorovebasso.exception.ImageNotFoundException;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_features.ProductFeaturesMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_features.ProductFeaturesRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;
import com.ortoroverbasso.ortorovebasso.service.product.product_features.IProductFeatures;

import jakarta.transaction.Transactional;

public class ProductFeaturesImpl implements IProductFeatures {
    @Autowired
    private IProductFeatures productFeaturesService;
    @Autowired
    private ProductFeaturesResponseDto productFeaturesResponseDto;
    @Autowired
    private IProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductFeaturesRepository productFeaturesRepository;
    @Autowired
    private ProductConnectFeaturesRequestDto productConnectFeaturesRequestDto;

    private ProductEntity findProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductFeaturesEntity findFeatureOrThrow(Long id) {
        return productFeaturesRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    @Transactional
    @Override
    public ProductFeaturesResponseDto createProductFeatures(Long productId, ProductFeaturesRequestDto productFeatures) {

        ProductFeaturesEntity productFeaturesEntity = ProductFeaturesMapper.toEntity(productFeatures);

        ProductFeaturesEntity savedFeature = productFeaturesRepository.save(productFeaturesEntity);

        ProductConnectFeaturesRequestDto featureConnectRequest = new ProductConnectFeaturesRequestDto(productId,
                List.of(savedFeature.getId()));

        connectFeatureToProduct(featureConnectRequest);

        return ProductFeaturesMapper.toResponseDto(savedFeature);
    }

    @Transactional
    public List<ProductFeaturesResponseDto> connectFeatureToProduct(
            ProductConnectFeaturesRequestDto featuresRequestDto) {

        ProductEntity product = findProductOrThrow(featuresRequestDto.getIdConnect());

        List<ProductFeaturesResponseDto> connected = new ArrayList<>();

        for (Long featureId : featuresRequestDto.getFeaturesId()) {
            ProductFeaturesEntity featureEntity = findFeatureOrThrow(featureId);

            product.getProductFeatures().add(featureEntity);

        }

        productRepository.save(product);

        for (Long featureId : featuresRequestDto.getFeaturesId()) {
            ProductFeaturesEntity featureEntity = findFeatureOrThrow(featureId);
            ProductFeaturesResponseDto featureDto = ProductFeaturesMapper.toResponseDto(featureEntity);
            connected.add(featureDto);
        }

        return connected;
    }

    @Override
    public ProductFeaturesResponseDto updateProductFeatures(Long productId, ProductFeaturesRequestDto productFeatures) {
        // Implementation here
        return null;
    }

    @Override
    public ProductFeaturesResponseDto getAllProductFeatures(Long productId) {
        // Implementation here
        return null;
    }

    @Override
    public void deleteProductFeatures(Long productId, String feature) {
        // Implementation here
    }

}
