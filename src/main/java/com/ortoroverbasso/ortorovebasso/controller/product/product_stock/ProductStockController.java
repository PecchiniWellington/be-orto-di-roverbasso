package com.ortoroverbasso.ortorovebasso.controller.product.product_stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_stock.IProductStockService;

@RestController
@RequestMapping("/product-stock")
public class ProductStockController {

    private final IProductStockService productStockService;

    @Autowired
    public ProductStockController(IProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @PostMapping
    public ProductStockResponseDto createProductStock(@RequestBody ProductStockRequestDto dto) {
        return productStockService.createProductStock(dto);
    }

    @GetMapping
    public List<ProductStockResponseDto> getAllProductStocks() {
        return productStockService.getAllProductStocks();
    }

    @GetMapping("/sku/{sku}")
    public ProductStockResponseDto getProductStockBySku(@PathVariable String sku) {
        return productStockService.getProductStockBySku(sku);
    }

    @PutMapping("/{id}")
    public ProductStockResponseDto updateProductStock(@PathVariable Long id, @RequestBody ProductStockRequestDto dto) {
        return productStockService.updateProductStock(id, dto);
    }
}
