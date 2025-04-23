package com.ortoroverbasso.ortorovebasso.controller.product.product_attributes;

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

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_attributes.IProductAttributesService;

@RestController
@RequestMapping("/api/products/{productId}/attributes")
public class ProductAttributesController {

    @Autowired
    private IProductAttributesService productAttributesService;

    @PostMapping
    public ProductAttributesResponseDto createProductAttribute(
            @PathVariable Long productId,
            @RequestBody ProductAttributesRequestDto productAttributesRequestDto) {
        return productAttributesService.createProductAttribute(productId, productAttributesRequestDto);
    }

    @GetMapping("/all")
    public List<ProductAttributesResponseDto> getAllProductAttributesByProductId(@PathVariable Long productId) {
        return productAttributesService.getAllProductAttributesByProductId(productId);
    }

    @GetMapping("/{attributesId}")
    public ProductAttributesResponseDto getProductAttributesById(
            @PathVariable Long productId,
            @PathVariable Long attributesId) {
        return productAttributesService.getProductAttributesById(productId, attributesId);
    }

    @PutMapping("/{attributesId}")
    public ProductAttributesResponseDto updateProductAttributes(
            @PathVariable Long productId,
            @PathVariable Long attributesId,
            @RequestBody ProductAttributesRequestDto productAttributesRequestDto) {
        return productAttributesService.updateProductAttributes(productId, attributesId, productAttributesRequestDto);
    }

    @DeleteMapping("/{attributesId}")
    public void deleteProductAttributes(@PathVariable Long productId, @PathVariable Long attributesId) {
        productAttributesService.deleteProductAttributes(productId, attributesId);
    }
}
