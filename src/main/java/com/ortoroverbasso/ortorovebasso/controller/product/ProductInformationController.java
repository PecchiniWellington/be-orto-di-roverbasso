package com.ortoroverbasso.ortorovebasso.controller.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.ProductInformation.ProductInformationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.ProductInformation.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product_information.IProductInformationService;

@RestController
@RequestMapping("/api/productinformationbysku")
public class ProductInformationController {

    private final IProductInformationService productInformationService;

    public ProductInformationController(IProductInformationService productInformationService) {
        this.productInformationService = productInformationService;
    }

    @GetMapping("/all")
    public List<ProductInformationResponseDto> getAllProductInformation() {
        return productInformationService.getAllProductInformation();
    }

    @GetMapping("/{sku}")
    public ProductInformationResponseDto getProductInformationBySku(@PathVariable String sku) {
        ProductInformationResponseDto productInfo = productInformationService.getProductInformationBySku(sku);
        return productInfo;
    }

    @PostMapping
    public ProductInformationResponseDto createProductInformation(
            @RequestBody ProductInformationRequestDto productInformationRequestDto) {
        ProductInformationResponseDto productInformationResponseDto = productInformationService
                .createProductInformation(productInformationRequestDto);
        return productInformationResponseDto;
    }
}
