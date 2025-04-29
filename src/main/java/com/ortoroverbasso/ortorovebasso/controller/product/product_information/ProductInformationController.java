package com.ortoroverbasso.ortorovebasso.controller.product.product_information;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_information.IProductInformationService;

@RestController
@RequestMapping("/api/products/{productId}/information")
public class ProductInformationController {

    private final IProductInformationService productInformationService;

    public ProductInformationController(IProductInformationService productInformationService) {
        this.productInformationService = productInformationService;
    }

    /*
     * @GetMapping("/{sku}")
     * public ProductInformationResponseDto getProductInformationBySku(@PathVariable
     * String sku) {
     * ProductInformationResponseDto productInfo =
     * productInformationService.getProductInformationBySku(sku);
     * return productInfo;
     * }
     */

    @GetMapping()
    public ProductInformationResponseDto getProductInformation(@PathVariable Long productId) {
        ProductInformationResponseDto productInfo = productInformationService.getProductInformation(productId);
        return productInfo;
    }

    @PostMapping
    public ProductInformationResponseDto createProductInformation(
            @PathVariable Long productId,
            @RequestBody ProductInformationRequestDto productInformationRequestDto) {
        ProductInformationResponseDto productInformationResponseDto = productInformationService
                .createProductInformation(productId, productInformationRequestDto);
        return productInformationResponseDto;
    }

    @PutMapping("/{id}")
    public ProductInformationResponseDto updateProductInformation(
            @PathVariable Long id,
            @RequestBody ProductInformationRequestDto productInformationRequestDto) {
        ProductInformationResponseDto productInformationResponseDto = productInformationService
                .updateProductInformation(id, productInformationRequestDto);
        return productInformationResponseDto;
    }

    @DeleteMapping("/{id}")
    public void deleteProductInformation(@PathVariable Long id) {
        productInformationService.deleteProductInformation(id);
    }
}
