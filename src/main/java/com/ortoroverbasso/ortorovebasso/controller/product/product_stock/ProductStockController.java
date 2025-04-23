package com.ortoroverbasso.ortorovebasso.controller.product.product_stock;

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

import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_stock.IProductStockService;

@RestController
@RequestMapping("/api/product-stock")
public class ProductStockController {

    @Autowired
    private IProductStockService productStockService;

    @PostMapping
    public ProductStockResponseDto createProductStock(@RequestBody ProductStockRequestDto dto) {
        return productStockService.createProductStock(dto);
    }

    @GetMapping("/{id}")
    public ProductStockResponseDto getProductStockById(@PathVariable Long id) {
        return productStockService.getProductStockById(id);
    }

    @GetMapping("/all")
    public List<ProductStockResponseDto> getAllProductStocks() {
        return productStockService.getAllProductStocks();
    }

    @PutMapping("/{id}")
    public ProductStockResponseDto updateProductStock(@PathVariable Long id, @RequestBody ProductStockRequestDto dto) {
        return productStockService.updateProductStock(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductStock(@PathVariable Long id) {
        productStockService.deleteProductStock(id);
    }

}
