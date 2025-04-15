package com.ortoroverbasso.ortorovebasso.controller.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product_pricing_info.ProductPricingInfoRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product_pricing_info.ProductPricingInfoResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product_pricing_info.IProductPricingInfoService;

@RestController
@RequestMapping("/api/products-pricing")
public class ProductPricingController {

    private final IProductPricingInfoService productPricingInfoService;

    public ProductPricingController(IProductPricingInfoService productPricingInfoService) {
        this.productPricingInfoService = productPricingInfoService;
    }

    @PostMapping
    public ProductPricingInfoResponseDto createProductPricingInfo(Long productId,
            @RequestBody ProductPricingInfoRequestDto dto) {
        return productPricingInfoService.createProductPricingInfo(productId, dto);
    }

    @GetMapping()
    public ProductPricingInfoResponseDto getProductPricingInfoBySku(String sku) {
        return productPricingInfoService.getProductPricingInfoBySku(sku);
    }

}
