package com.ortoroverbasso.ortorovebasso.controller.product.product_attributes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_attributes.ProductAttributesResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_attributes.IProductAttributesService;

@RestController
@RequestMapping("/api/product-attributes")
public class ProductAttributesController {
    private final IProductAttributesService productAttributesService;

    @Autowired
    public ProductAttributesController(IProductAttributesService productAttributesService) {
        this.productAttributesService = productAttributesService;
    }

    @GetMapping("/{productId}")
    public List<ProductAttributesResponseDto> getProductAttributesByProductId(@PathVariable Long productId) {
        return productAttributesService.getProductAttributesByProductId(productId);
    }

    @PostMapping
    public ProductAttributesResponseDto saveProductAttributes(
            @RequestBody ProductAttributesRequestDto productAttributesRequestDto) {
        return productAttributesService.saveProductAttributes(productAttributesRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductAttributes(@PathVariable Long id) {
        productAttributesService.deleteProductAttributes(id);
    }

    @GetMapping("/{id}")
    public ProductAttributesResponseDto getProductAttributesById(@PathVariable Long id) {
        return productAttributesService.getProductAttributesById(id);
    }

    @GetMapping
    public List<ProductAttributesResponseDto> getAllProductAttributes() {
        return productAttributesService.getAllProductAttributes();
    }
}
