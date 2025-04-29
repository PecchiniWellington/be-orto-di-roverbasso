package com.ortoroverbasso.ortorovebasso.controller.product.product_features;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_features.IProductFeatures;

@RestController
@RequestMapping("/api/products/{productId}/features")
public class ProductFeaturesController {

    @Autowired
    private IProductFeatures productFeaturesService;

    @GetMapping()
    public ProductFeaturesResponseDto getAllProductFeatures(@RequestParam Long productId) {
        return productFeaturesService.getAllProductFeatures(productId);
    }

    @PutMapping("/{id}")
    public ProductFeaturesResponseDto updateProductFeatures(
            @PathVariable Long productId,
            @RequestBody ProductFeaturesRequestDto productFeatures) {
        return productFeaturesService.updateProductFeatures(
                productId,
                productFeatures);
    }

    @PostMapping()
    public ProductFeaturesResponseDto createProductFeatures(
            @PathVariable Long productId,
            @RequestBody ProductFeaturesRequestDto productFeatures) {
        return productFeaturesService.createProductFeatures(
                productId,
                productFeatures);
    }

    @DeleteMapping("/{id}")
    public void deleteProductFeatures(
            @PathVariable Long productId,
            @RequestParam String feature) {
        productFeaturesService.deleteProductFeatures(productId, feature);
    }

}
