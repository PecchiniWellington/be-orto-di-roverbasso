package com.ortoroverbasso.ortorovebasso.service.product.product_stock.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_stock.ProductStockResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_stock.ProductStock;
import com.ortoroverbasso.ortorovebasso.entity.product.product_stock.ProductStockEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_stock.ProductStockMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.product_stock.ProductStockRepository;
import com.ortoroverbasso.ortorovebasso.service.product.product_stock.IProductStockService;

@Service
public class ProductStockServiceImpl implements IProductStockService {

    private final ProductStockRepository productStockRepository;

    @Autowired
    public ProductStockServiceImpl(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    @Override
    public ProductStockResponseDto createProductStock(ProductStockRequestDto dto) {
        ProductStockEntity productStock = ProductStockMapper.toEntity(dto);
        productStock = productStockRepository.save(productStock);
        return ProductStockMapper.toResponseDto(productStock);
    }

    @Override
    public ProductStockResponseDto updateProductStock(Long id, ProductStockRequestDto dto) {
        ProductStockEntity productStockEntity = productStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Stock not found"));

        productStockEntity.setSku(dto.getSku());

        List<ProductStock> updatedProductStocks = dto.getStocks().stream()
                .map(stockDto -> {
                    ProductStock updatedStock = new ProductStock();
                    updatedStock.setQuantity(stockDto.getQuantity());
                    updatedStock.setMinHandlingDays(stockDto.getMinHandlingDays());
                    updatedStock.setMaxHandlingDays(stockDto.getMaxHandlingDays());
                    updatedStock.setWarehouse(stockDto.getWarehouse());
                    return updatedStock;
                })
                .collect(Collectors.toList());

        productStockEntity.setStocks(updatedProductStocks);
        productStockEntity = productStockRepository.save(productStockEntity);

        return ProductStockMapper.toResponseDto(productStockEntity);
    }

    @Override
    public List<ProductStockResponseDto> getAllProductStocks() {
        List<ProductStockEntity> productStocks = productStockRepository.findAll();
        return ProductStockMapper.toResponseDto(productStocks);
    }

    @Override
    public ProductStockResponseDto getProductStockBySku(String sku) {
        ProductStockEntity productStock = productStockRepository.findBySku(sku);
        return ProductStockMapper.toResponseDto(productStock);
    }

    @Override
    public ProductStockResponseDto getProductStockById(Long id) {
        ProductStockEntity productStock = productStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Stock not found"));
        return ProductStockMapper.toResponseDto(productStock);
    }

    @Override
    public void deleteProductStock(Long id) {
        ProductStockEntity productStock = productStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Stock not found"));
        productStockRepository.delete(productStock);
    }
}
