package com.ortoroverbasso.ortorovebasso.controller.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product_price_large_quantity.ProductPriceLargeQuantityRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product_price_large_quantity.ProductPriceLargeQuantityResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;
import com.ortoroverbasso.ortorovebasso.service.product_price_large_quantity.IProductPriceLargeQuantityService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;
    private final IProductPriceLargeQuantityService productPriceLargeQuantityService;

    public ProductController(IProductService productService,
            IProductPriceLargeQuantityService productPriceLargeQuantityService) {
        this.productPriceLargeQuantityService = productPriceLargeQuantityService;
        this.productService = productService;
    }

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto dto) {
        return productService.createProduct(dto);
    }

    @GetMapping()
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}/prices")
    public List<ProductPriceLargeQuantityResponseDto> getProductPrices(@PathVariable Long productId) {
        return productPriceLargeQuantityService.getProductPricesLargeQuantityByProductId(productId);

    };

    @PostMapping("/{productId}/prices")
    public ProductPriceLargeQuantityResponseDto createProductPrice(
            @PathVariable Long productId,
            @RequestBody ProductPriceLargeQuantityRequestDto dto) {
        return productPriceLargeQuantityService.createProductPriceLargeQuantity(productId, dto);
    }

}
