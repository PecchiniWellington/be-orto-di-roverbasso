package com.ortoroverbasso.ortorovebasso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.service.product.impl.ProductServiceImpl;

@RestController
@RequestMapping("/api/productinformationbysku/{sku}")
public class ProductInformationController {

    private final ProductServiceImpl productServiceImpl;

    public ProductInformationController(ProductServiceImpl productService, ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productService;
    }
}
