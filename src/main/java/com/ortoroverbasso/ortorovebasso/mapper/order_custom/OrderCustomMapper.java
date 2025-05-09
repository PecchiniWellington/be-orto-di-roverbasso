package com.ortoroverbasso.ortorovebasso.mapper.order_custom;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.order_custom.OrderCustomEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.mapper.pickup.PickupMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;

@Component
public class OrderCustomMapper {

    @Autowired
    private ProductRepository productRepository;

    public OrderCustomResponseDto toDto(OrderCustomEntity entity) {
        if (entity == null) {
            return null;
        }

        OrderCustomResponseDto dto = new OrderCustomResponseDto();
        dto.setId(entity.getId());
        dto.setToken(entity.getToken());
        dto.setStatusOrder(entity.getStatusOrder());

        if (entity.getProducts() != null) {
            dto.setProducts(entity.getProducts().stream()
                    .map(ProductMapper::toResponseDto)
                    .collect(Collectors.toList()));
        }

        if (entity.getPickupOrder() != null) {
            dto.setPickupOrder(PickupMapper.toDto(entity.getPickupOrder()));
        }

        return dto;
    }

    public OrderCustomEntity toEntity(OrderCustomRequestDto dto) {
        if (dto == null) {
            return null;
        }

        OrderCustomEntity entity = new OrderCustomEntity();

        // Set default status
        entity.setStatusOrder("PENDING");

        // Generate a token
        entity.setToken(UUID.randomUUID().toString());

        // Set products from productIds
        if (dto.getProductIds() != null && !dto.getProductIds().isEmpty()) {
            List<ProductEntity> products = new ArrayList<>();

            for (Long productId : dto.getProductIds()) {
                productRepository.findById(productId).ifPresent(products::add);
            }

            entity.setProducts(products);
        } else {
            entity.setProducts(new ArrayList<>());
        }

        // Set the already saved PickupEntity
        if (dto.getPickupOrder() != null) {
            entity.setPickupOrder(dto.getPickupOrder());
        }

        return entity;
    }

    public OrderCustomEntity updateEntityFromDto(OrderCustomRequestDto dto, OrderCustomEntity entity) {
        if (dto == null) {
            return entity;
        }

        // Update products if provided
        if (dto.getProductIds() != null && !dto.getProductIds().isEmpty()) {
            List<ProductEntity> products = new ArrayList<>();

            for (Long productId : dto.getProductIds()) {
                productRepository.findById(productId).ifPresent(products::add);
            }

            entity.setProducts(products);
        }

        // Update pickup order if provided
        if (dto.getPickupOrder() != null) {
            entity.setPickupOrder(dto.getPickupOrder());
        }

        return entity;
    }
}
