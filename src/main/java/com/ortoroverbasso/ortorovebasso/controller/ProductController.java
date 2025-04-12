package com.ortoroverbasso.ortorovebasso.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.Product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.Product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.mapper.ProductMapper;
import com.ortoroverbasso.ortorovebasso.model.Product;
import com.ortoroverbasso.ortorovebasso.service.product.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto dto) {
        Product product = ProductMapper.toEntity(dto);
        Product savedProduct = productService.createProduct(product);
        return ProductMapper.toResponseDto(savedProduct);
    }
}
