package com.ortoroverbasso.ortorovebasso.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_pricing.ProductPricingRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;
import com.ortoroverbasso.ortorovebasso.service.product.product_large_quantity_price.IProductLargeQuantityPriceService;
import com.ortoroverbasso.ortorovebasso.service.tags.IProductTagsService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private IProductTagsService productTagsService;
    @Autowired
    private IProductLargeQuantityPriceService productLargeQuantityPriceService;

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto dto) {
        return productService.createProduct(dto);
    }

    @GetMapping("/all")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<GenericResponseDto> deleteProduct(@PathVariable Long productId) {
        GenericResponseDto response = productService.deleteProduct(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);

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

    @GetMapping("/{productId}/tags")
    public ProductTagsResponseDto getProductTags(@PathVariable Long productId) {
        return productTagsService.getProductTagsById(productId);
    }

    @PostMapping("/{productId}/tags")
    public ProductTagsResponseDto createProductTag(
            @PathVariable Long productId,
            @RequestBody ProductTagsRequestDto productTagsRequestDto) {
        return productTagsService.createProductTag(productTagsRequestDto);
    }

    @PutMapping("/{productId}/tags/{tagId}")
    public ProductTagsResponseDto updateProductTag(
            @PathVariable Long productId,
            @PathVariable Long tagId,
            @RequestBody ProductTagsRequestDto productTagsRequestDto) {
        return productTagsService.updateProductTag(productId, productTagsRequestDto);
    }

    @DeleteMapping("/{productId}/tags/{tagId}")
    public void deleteProductTag(@PathVariable Long productId, @PathVariable Long tagId) {
        productTagsService.deleteProductTag(tagId);
    }

    @GetMapping("/product-large-quantity-prices/all")
    public List<ProductLargeQuantityPriceResponseDto> getProductLargeQuantityPrice() {
        return productLargeQuantityPriceService.getProductLargeQuantityPrice();
    }

    @GetMapping("/{productId}/product-large-quantity-prices")
    public List<ProductLargeQuantityPriceResponseDto> getProductLargeQuantityPriceByProductId(
            @PathVariable Long productId) {
        return productLargeQuantityPriceService.getProductLargeQuantityPriceByProductId(productId);
    }

    @PostMapping("/product-large-quantity-prices")
    public ProductLargeQuantityPriceResponseDto createProductLargeQuantityPrice(
            @RequestBody ProductLargeQuantityPriceRequestDto productPriceLargeQuantityRequestDto) {
        return productLargeQuantityPriceService.createProductPriceLargeQuantity(
                productPriceLargeQuantityRequestDto);
    }

}
