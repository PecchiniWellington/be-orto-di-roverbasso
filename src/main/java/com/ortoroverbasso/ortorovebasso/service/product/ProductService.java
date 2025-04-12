package com.ortoroverbasso.ortorovebasso.service.product;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.model.Product;
import com.ortoroverbasso.ortorovebasso.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
