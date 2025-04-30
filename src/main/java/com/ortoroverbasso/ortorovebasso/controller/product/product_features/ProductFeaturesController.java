package com.ortoroverbasso.ortorovebasso.controller.product.product_features;

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

import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_features.IProductFeaturesService;

@RestController
@RequestMapping("/api/products/{productId}/features")
public class ProductFeaturesController {

    @Autowired
    private IProductFeaturesService productFeaturesService;

    @PostMapping()
    public ProductFeaturesResponseDto createProductFeatures(
            @PathVariable Long productId,
            @RequestBody ProductFeaturesRequestDto productFeatures) {
        return productFeaturesService.createProductFeatures(
                productId,
                productFeatures);
    }

    @GetMapping()
    public List<ProductFeaturesResponseDto> getAllProductFeatures(@PathVariable Long productId) {
        return productFeaturesService.getAllProductFeatures(productId);
    }

    @PutMapping("/{id}")
    public ProductFeaturesResponseDto updateProductFeatures(
            @PathVariable Long productId,
            @RequestBody ProductFeaturesRequestDto productFeatures,
            @PathVariable Long id) {
        return productFeaturesService.updateProductFeatures(
                productId,
                productFeatures,
                id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductFeatures(
            @PathVariable Long productId,
            @PathVariable Long id) {
        productFeaturesService.deleteProductFeatures(productId, id);
    }

}
