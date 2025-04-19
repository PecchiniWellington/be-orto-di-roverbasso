package com.ortoroverbasso.ortorovebasso.mapper.orders;

import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.orders.OrderRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.orders.OrderResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.order.OrderEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.mapper.shipping.CarrierMapper;
import com.ortoroverbasso.ortorovebasso.mapper.user.shipping_address.ShippingAddressMapper;

public class OrderMapper {

    public static OrderEntity toEntity(OrderRequestDto dto) {
        OrderEntity entity = new OrderEntity();
        entity.setInternalReference(dto.getInternalReference());
        entity.setDateAdd(dto.getDateAdd());
        entity.setTotalPaidTaxIncl(dto.getTotalPaidTaxIncl());
        entity.setTotalPaidTaxExcl(dto.getTotalPaidTaxExcl());
        entity.setTotalShippingTaxExcl(dto.getTotalShippingTaxExcl());
        entity.setTotalShippingTaxIncl(dto.getTotalShippingTaxIncl());
        entity.setStatus(dto.getStatus());

        // Mappare ShippingAddress, Products, and Carriers
        entity.setShippingAddress(ShippingAddressMapper.toEntity(dto.getShippingAddress()));
        entity.setProducts(dto.getProducts().stream()
                .map(ProductMapper::toEntity)
                .collect(Collectors.toList()));
        entity.setCarriers(dto.getCarriers().stream()
                .map(CarrierMapper::toEntity)
                .collect(Collectors.toList()));

        return entity;
    }

    public static OrderResponseDto toResponse(OrderEntity entity) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(entity.getId());
        dto.setInternalReference(entity.getInternalReference());
        dto.setDateAdd(entity.getDateAdd());
        dto.setTotalPaidTaxIncl(entity.getTotalPaidTaxIncl());
        dto.setTotalPaidTaxExcl(entity.getTotalPaidTaxExcl());
        dto.setTotalShippingTaxExcl(entity.getTotalShippingTaxExcl());
        dto.setTotalShippingTaxIncl(entity.getTotalShippingTaxIncl());
        dto.setStatus(entity.getStatus());

        // Mappare ShippingAddress, Products, and Carriers
        dto.setShippingAddress(ShippingAddressMapper.toResponse(entity.getShippingAddress()));
        dto.setProducts(entity.getProducts().stream()
                .map(ProductMapper::toResponseDto)
                .collect(Collectors.toList()));
        dto.setCarriers(entity.getCarriers().stream()
                .map(CarrierMapper::toResponse)
                .collect(Collectors.toList()));

        return dto;
    }
}
