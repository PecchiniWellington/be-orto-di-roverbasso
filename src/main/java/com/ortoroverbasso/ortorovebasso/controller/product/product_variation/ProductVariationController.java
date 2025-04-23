package com.ortoroverbasso.ortorovebasso.controller.product.product_variation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.ProductVariationResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_variation.impl.ProductVariationService;

@RestController
@RequestMapping("/api/products/{productId}/variations")
public class ProductVariationController {

    @Autowired
    private ProductVariationService productVariationService;

    @PostMapping()
    public ProductVariationResponseDto createProductVariation(
            @PathVariable Long productId,
            @RequestBody ProductVariationRequestDto productVariationRequestDto) {
        return productVariationService.createProductVariation(productId, productVariationRequestDto);
    }

    @GetMapping("/all")
    public List<ProductVariationResponseDto> getAllProductVariations(@PathVariable Long productId) {
        return productVariationService.getAllProductVariations(productId);
    }

    @GetMapping("/{variationId}")
    public ProductVariationResponseDto getProductVariationById(
            @PathVariable Long productId,
            @PathVariable Long variationId) {
        return productVariationService.getProductVariationById(productId, variationId);
    }

    @PutMapping("/{variationId}")
    public ProductVariationResponseDto updateProductVariation(
            @PathVariable Long productId,
            @PathVariable Long variationId,
            @RequestBody ProductVariationRequestDto productVariationRequestDto) {
        return productVariationService.updateProductVariation(productId, variationId, productVariationRequestDto);
    }

    @DeleteMapping("/{variationId}")
    public void deleteProductVariation(
            @PathVariable Long productId,
            @PathVariable Long variationId) {
        productVariationService.deleteVariation(variationId);
    }
}
