package com.ortoroverbasso.ortorovebasso.service.product.impl;

import org.springframework.stereotype.Service;

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
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // qui implementerò altri metodi richiesti dall'interfaccia IProductService
}
