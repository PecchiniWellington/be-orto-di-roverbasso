package com.ortoroverbasso.ortorovebasso.mapper.orders;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.orders.OrderRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.orders.OrderResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.CarrierResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.order.OrderEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.shipping.CarriersEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.shipping_address.ShippingAddressEntity;

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

        // Mapping Shipping Address
        ShippingAddressEntity shippingAddress = new ShippingAddressEntity();
        shippingAddress.setFirstName(dto.getShippingAddress().getFirstName());
        shippingAddress.setLastName(dto.getShippingAddress().getLastName());
        shippingAddress.setCountry(dto.getShippingAddress().getCountry());
        shippingAddress.setPostcode(dto.getShippingAddress().getPostcode());
        shippingAddress.setTown(dto.getShippingAddress().getTown());
        shippingAddress.setAddress(dto.getShippingAddress().getAddress());
        shippingAddress.setPhone(dto.getShippingAddress().getPhone());
        shippingAddress.setEmail(dto.getShippingAddress().getEmail());
        shippingAddress.setVatNumber(dto.getShippingAddress().getVatNumber());
        shippingAddress.setCompanyName(dto.getShippingAddress().getCompanyName());
        shippingAddress.setComment(dto.getShippingAddress().getComment());
        entity.setShippingAddress(shippingAddress);

        // Mapping Products
        List<ProductEntity> products = dto.getProducts().stream()
                .map(productDto -> new ProductEntity(productDto.getReference(), productDto.getQuantity()))
                .collect(Collectors.toList());
        entity.setProducts(products);

        // Mapping Carriers
        List<CarriersEntity> carriers = dto.getCarriers().stream()
                .map(carrierDto -> new CarriersEntity(carrierDto.getName()))
                .collect(Collectors.toList());
        entity.setCarriers(carriers);

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

        // Mapping Shipping Address
        ShippingAddressEntity shippingAddressEntity = entity.getShippingAddress();
        ShippingAddressResponseDto shippingAddressDto = new ShippingAddressResponseDto();
        shippingAddressDto.setFirstName(shippingAddressEntity.getFirstName());
        shippingAddressDto.setLastName(shippingAddressEntity.getLastName());
        shippingAddressDto.setCountry(shippingAddressEntity.getCountry());
        shippingAddressDto.setPostcode(shippingAddressEntity.getPostcode());
        shippingAddressDto.setTown(shippingAddressEntity.getTown());
        shippingAddressDto.setAddress(shippingAddressEntity.getAddress());
        shippingAddressDto.setPhone(shippingAddressEntity.getPhone());
        shippingAddressDto.setEmail(shippingAddressEntity.getEmail());
        shippingAddressDto.setVatNumber(shippingAddressEntity.getVatNumber());
        shippingAddressDto.setCompanyName(shippingAddressEntity.getCompanyName());
        shippingAddressDto.setComment(shippingAddressEntity.getComment());
        dto.setShippingAddress(shippingAddressDto);

        // Mapping Products
        List<ProductResponseDto> productDtos = entity.getProducts().stream()
                .map(productEntity -> new ProductResponseDto(productEntity.getReference(), productEntity.getQuantity()))
                .collect(Collectors.toList());
        dto.setProducts(productDtos);

        // Mapping Carriers
        List<CarrierResponseDto> carrierDtos = entity.getCarriers().stream()
                .map(carrierEntity -> new CarrierResponseDto(carrierEntity.getId(), carrierEntity.getName(),
                        carrierEntity.getPrice()))
                .collect(Collectors.toList());
        dto.setCarriers(carrierDtos);

        return dto;
    }
}
