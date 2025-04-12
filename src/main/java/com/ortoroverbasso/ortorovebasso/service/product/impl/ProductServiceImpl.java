package com.ortoroverbasso.ortorovebasso.service.product.impl;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.Product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.Product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.mapper.ProductMapper;
import com.ortoroverbasso.ortorovebasso.model.Product;
import com.ortoroverbasso.ortorovebasso.repository.ProductRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = ProductMapper.toEntity(productRequestDto);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponseDto(savedProduct);
    }

    // qui implementerò altri metodi richiesti dall'interfaccia IProductService
}
