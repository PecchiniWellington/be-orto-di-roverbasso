package com.ortoroverbasso.ortorovebasso.service.product.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_informations.ProductInformationEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_pricing.ProductPricingEntity;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_information.ProductInformationMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_information.ProductInformationRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_pricing_repository.ProductPricingRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductInformationRepository productInformationRepository;
    private final ProductPricingRepository productPricingInfoRepository;

    public ProductServiceImpl(ProductRepository productRepository,
            ProductInformationRepository productInformationRepository,
            ProductPricingRepository productPricingInfoRepository) {
        this.productPricingInfoRepository = productPricingInfoRepository;
        this.productRepository = productRepository;
        this.productInformationRepository = productInformationRepository;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {

        ProductEntity product = ProductMapper.toEntity(dto);

        product = productRepository.save(product);

        return ProductMapper.toResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return ProductMapper.toResponseDto(products);
    }

    @Override
    public ProductEntity getProductById(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException(
                "Product not found with ID: " + productId));

        throw new RuntimeException("Product not found with Id: " + productId);
    }

    @Override
    public ProductResponseDto createProductPriceInfo(Long productId, ProductPricingRequestDto productPriceInfo) {
        try {
            ProductEntity product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            ProductPricingEntity productPricingInfoEntity = new ProductPricingEntity();
            productPricingInfoEntity.setSku(product.getSku());
            productPricingInfoEntity.setWholesalePrice(productPriceInfo.getWholesalePrice());
            productPricingInfoEntity.setRetailPrice(productPriceInfo.getRetailPrice());
            productPricingInfoEntity.setInShopsPrice(productPriceInfo.getInShopsPrice());

            /* productPricingInfoEntity.setProduct(product); */

            productPricingInfoRepository.save(productPricingInfoEntity);

            return new ProductResponseDto(
                    product.getId(),
                    product.getSku(),
                    product.getRetailPrice(),
                    product.getCategory(),
                    product.getWeight(),
                    product.getActive() == 1,
                    productPricingInfoEntity.getWholesalePrice(),
                    productPricingInfoEntity.getInShopsPrice());

        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating product pricing info.", e);
        }
    }

    @Override
    public List<ProductInformationResponseDto> getProductPrices(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        ProductInformationEntity productInformationPrice = productInformationRepository.findBySku(product.getSku());

        if (productInformationPrice != null) {
            return Collections.singletonList(ProductInformationMapper.toResponseDto(productInformationPrice));

        } else {
            return Collections.emptyList();
        }

    }

}
