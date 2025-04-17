package com.ortoroverbasso.ortorovebasso.controller.product.product_variation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_variation.impl.ProductVariationService;

@RestController
@RequestMapping("/productvariations")
public class ProductVariationController {

    @Autowired
    private ProductVariationService productVariationService;

    @GetMapping("/{id}")
    public ProductVariationResponseDto getProductVariation(@PathVariable Long id) {
        return productVariationService.getProductVariationById(id);
    }

    @GetMapping
    public List<ProductVariationResponseDto> getAllProductVariations() {
        return productVariationService.getAllProductVariations();
    }
}
