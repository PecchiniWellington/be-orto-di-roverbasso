package com.ortoroverbasso.ortorovebasso.mapper.product.product_stock;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_stock.ProductStock;
import com.ortoroverbasso.ortorovebasso.entity.product.product_stock.ProductStockEntity;

public class ProductStockMapper {

    public static ProductStockEntity toEntity(ProductStockRequestDto dto) {
        ProductStockEntity productStockEntity = new ProductStockEntity();

        productStockEntity.setSku(dto.getSku());

        List<ProductStock> productStocks = dto.getStocks().stream()
                .map(stockDto -> {
                    ProductStock productStock = new ProductStock();
                    productStock.setQuantity(stockDto.getQuantity());
                    productStock.setMinHandlingDays(stockDto.getMinHandlingDays());
                    productStock.setMaxHandlingDays(stockDto.getMaxHandlingDays());
                    productStock.setWarehouse(stockDto.getWarehouse());
                    return productStock;
                })
                .collect(Collectors.toList());

        productStockEntity.setStocks(productStocks);

        return productStockEntity;
    }

    public static ProductStockResponseDto toResponseDto(ProductStockEntity productStockEntity) {
        if (productStockEntity == null) {
            return null;
        }

        List<ProductStockDto> productStockDtos = productStockEntity.getStocks().stream()
                .map(stock -> new ProductStockDto(
                        stock.getQuantity(),
                        stock.getMinHandlingDays(),
                        stock.getMaxHandlingDays(),
                        stock.getWarehouse()))
                .collect(Collectors.toList());

        return new ProductStockResponseDto(
                (int) (long) productStockEntity.getId(),
                productStockEntity.getSku(),
                productStockDtos);
    }

    public static List<ProductStockResponseDto> toResponseDto(List<ProductStockEntity> productStocks) {
        return productStocks.stream()
                .map(ProductStockMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
