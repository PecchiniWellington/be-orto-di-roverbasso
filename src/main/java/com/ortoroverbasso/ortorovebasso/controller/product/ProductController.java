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
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingRequestDto;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;
import com.ortoroverbasso.ortorovebasso.service.product.product_large_quantity_price.IProductLargeQuantityPriceService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;
    private final IProductLargeQuantityPriceService productPriceLargeQuantityService;

    public ProductController(
            IProductService productService,
            IProductLargeQuantityPriceService productPriceLargeQuantityService) {
        this.productService = productService;
        this.productPriceLargeQuantityService = productPriceLargeQuantityService;
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
    public List<ProductInformationResponseDto> getProductPrices(@PathVariable Long productId) {
        return productService.getProductPrices(productId);

    };

    @PostMapping("/{productId}/prices")
    public ProductResponseDto createProductPrice(
            @PathVariable Long productId,
            @RequestBody ProductPricingRequestDto productPriceInfo) {
        return productService.createProductPriceInfo(productId, productPriceInfo);
    }

}
