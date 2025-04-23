package com.ortoroverbasso.ortorovebasso.service.product.product_stock;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockResponseDto;

public interface IProductStockService {

    ProductStockResponseDto createProductStock(ProductStockRequestDto dto);

    ProductStockResponseDto updateProductStock(Long id, ProductStockRequestDto dto);

    List<ProductStockResponseDto> getAllProductStocks();

    ProductStockResponseDto getProductStockById(Long id);

    void deleteProductStock(Long id);

    ProductStockResponseDto getProductStockBySku(String sku);

}
