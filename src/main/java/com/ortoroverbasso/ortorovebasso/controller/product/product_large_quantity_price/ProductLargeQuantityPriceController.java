package com.ortoroverbasso.ortorovebasso.controller.product.product_large_quantity_price;

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

import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_large_quantity_price.IProductLargeQuantityPriceService;

@RestController
@RequestMapping("/api/products/{productId}/large-quantity-prices")
public class ProductLargeQuantityPriceController {

    @Autowired
    private IProductLargeQuantityPriceService productLargeQuantityPriceService;

    @GetMapping()
    public List<ProductLargeQuantityPriceResponseDto> getProductLargeQuantityPriceByProductId(
            @PathVariable Long productId) {
        return productLargeQuantityPriceService.getProductLargeQuantityPriceByProductId(productId);
    }

    @PostMapping()
    public ProductLargeQuantityPriceResponseDto createProductLargeQuantityPrice(
            @PathVariable Long productId,
            @RequestBody ProductLargeQuantityPriceRequestDto productPriceLargeQuantityRequestDto) {
        return productLargeQuantityPriceService.createProductPriceLargeQuantity(
                productId,
                productPriceLargeQuantityRequestDto);
    }

    @PutMapping("/{priceId}")
    public ProductLargeQuantityPriceResponseDto updateProductLargeQuantityPrice(
            @PathVariable Long productId,
            @PathVariable Long priceId,
            @RequestBody ProductLargeQuantityPriceRequestDto productPriceLargeQuantityRequestDto) {
        return productLargeQuantityPriceService.updateProductPriceLargeQuantity(
                productId,
                priceId,
                productPriceLargeQuantityRequestDto);
    }

    @DeleteMapping("/{priceId}")
    public String deleteProductLargeQuantityPrice(@PathVariable Long priceId) {
        return productLargeQuantityPriceService.deleteProductPriceLargeQuantity(priceId);
    }

}
