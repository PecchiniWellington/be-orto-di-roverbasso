package com.ortoroverbasso.ortorovebasso.service.product.product_variation.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
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
    public List<ProductVariationResponseDto> getAllProductVariations(Long productId) {
        List<ProductVariationEntity> variations = productVariationRepository.findAllByProductId(productId);
        return variations.stream()
                .map(ProductVariationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductVariationResponseDto getProductVariationById(Long productId, Long variationId) {
        ProductVariationEntity entity = productVariationRepository
                .findByProductIdAndId(productId, variationId);

        if (entity == null) {
            throw new RuntimeException("Product variation not found for this product");
        }

        return ProductVariationMapper.toResponse(entity);
    }

    @Override
    public ProductVariationResponseDto createProductVariation(
            Long productId,
            ProductVariationRequestDto variationRequestDto) {
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

    @Override
    public ProductVariationResponseDto updateProductVariation(
            Long productId,
            Long variationId,
            ProductVariationRequestDto variationRequestDto) {

        ProductVariationEntity productVariationEntity = productVariationRepository
                .findByProductIdAndId(productId, variationId);
        if (productVariationEntity == null) {
            throw new RuntimeException("Product variation not found for this product");
        }
        productVariationEntity.setSku(variationRequestDto.getSku());
        productVariationEntity.setEan13(variationRequestDto.getEan13());
        productVariationEntity.setExtraWeight(variationRequestDto.getExtraWeight());
        productVariationEntity.setWholesalePrice(variationRequestDto.getWholesalePrice());
        productVariationEntity.setRetailPrice(variationRequestDto.getRetailPrice());
        productVariationEntity.setInShopsPrice(variationRequestDto.getInShopsPrice());
        productVariationEntity.setWidth(variationRequestDto.getWidth());
        productVariationEntity.setHeight(variationRequestDto.getHeight());
        productVariationEntity.setDepth(variationRequestDto.getDepth());
        productVariationEntity.setLogisticClass(variationRequestDto.getLogisticClass());
        productVariationEntity.setPartNumber(variationRequestDto.getPartNumber());
        productVariationEntity.setCanon(variationRequestDto.getCanon());
        productVariationEntity.setPriceLargeQuantities(variationRequestDto.getPriceLargeQuantities()
                .stream()
                .map(priceDto -> new ProductLargeQuantityPriceEntity(priceDto.getQuantity(), priceDto.getPrice()))
                .collect(Collectors.toList()));

        productVariationEntity = productVariationRepository.save(productVariationEntity);

        return ProductVariationMapper.toResponse(productVariationEntity);

    }

}
