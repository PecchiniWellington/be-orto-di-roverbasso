package com.ortoroverbasso.ortorovebasso.service.product.product_variation.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_variation.ProductVariationEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_variation.ProductVariationMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_variation.ProductVariationRepository;
import com.ortoroverbasso.ortorovebasso.service.product.product_variation.IProductVariationService;

@Service
public class ProductVariationService implements IProductVariationService {

    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductVariationResponseDto> getAllProductVariations() {
        List<ProductVariationEntity> entities = productVariationRepository.findAll();
        return entities.stream().map(ProductVariationMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProductVariationResponseDto getProductVariationById(Long id) {
        ProductVariationEntity entity = productVariationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product variation not found"));
        return ProductVariationMapper.toResponse(entity);
    }

    @Override
    public ProductVariationResponseDto createVariation(Long productId, ProductVariationRequestDto variationRequestDto) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductVariationEntity productVariationEntity = ProductVariationMapper.toEntity(variationRequestDto);

        productVariationEntity.setProduct(productEntity);

        productVariationEntity = productVariationRepository.save(productVariationEntity);

        return ProductVariationMapper.toResponse(productVariationEntity);
    }

    @Override
    public void deleteVariation(Long id) {
        ProductVariationEntity productVariationEntity = productVariationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Variation not found"));

        productVariationRepository.delete(productVariationEntity);
    }

}
