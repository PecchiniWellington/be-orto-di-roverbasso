package com.ortoroverbasso.ortorovebasso.controller.product.product_pricing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_pricing.IProductPricingService;

@RestController
@RequestMapping("/api/products-pricing")
public class ProductPricingController {

    private final IProductPricingService productPricingService;

    public ProductPricingController(IProductPricingService productPricingService) {
        this.productPricingService = productPricingService;
    }

    @GetMapping()
    public ProductPricingResponseDto getProductPricingBySku(String sku) {
        return productPricingService.getProductPricingBySku(sku);
    }

}
