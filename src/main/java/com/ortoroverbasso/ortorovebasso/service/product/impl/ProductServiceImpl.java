package com.ortoroverbasso.ortorovebasso.service.product.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductInformationRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductPriceLargeQuantityRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductInformationRepository productInformationRepository;
    private final ProductPriceLargeQuantityRepository productPriceLargeQuantityRepository;

    public ProductServiceImpl(ProductRepository productRepository,
            ProductInformationRepository productInformationRepository,
            ProductPriceLargeQuantityRepository productPriceLargeQuantityRepository) {
        this.productRepository = productRepository;
        this.productInformationRepository = productInformationRepository;
        this.productPriceLargeQuantityRepository = productPriceLargeQuantityRepository;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {

        ProductEntity product = ProductMapper.toEntity(dto);

        product = productRepository.save(product);

        return ProductMapper.toResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        // Mapping a list of products to response DTOs
        List<ProductEntity> products = productRepository.findAll();
        return ProductMapper.toResponseDto(products);
    }

    @Override
    public ProductResponseDto getProductBySku(String sku) {
        // Get product by SKU and map it to the response DTO
        ProductEntity product = productRepository.findBySku(sku);
        if (product != null) {
            return ProductMapper.toResponseDto(product);
        }
        throw new RuntimeException("Product not found with SKU: " + sku);
    }

    @Override
    public ProductEntity getProductById(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException(
                "Product not found with ID: " + productId));

        throw new RuntimeException("Product not found with Id: " + productId);
    }

}
