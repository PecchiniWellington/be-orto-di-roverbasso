package com.ortoroverbasso.ortorovebasso.mapper.orders;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.orders.OrderRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.orders.OrderResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductQuantityDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.CarrierResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.order.OrderEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.shipping.CarriersEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.shipping_address.ShippingAddressEntity;
import com.ortoroverbasso.ortorovebasso.mapper.base.BaseMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;

@Component
public class OrderMapper implements BaseMapper<OrderEntity, OrderRequestDto, OrderResponseDto> {

        @Autowired
        private ProductMapper productMapper;

        @Override
        public OrderEntity toEntity(OrderRequestDto dto) {
                if (dto == null) {
                        return null;
                }

                OrderEntity entity = new OrderEntity();
                entity.setInternalReference(dto.getInternalReference());
                entity.setDateAdd(dto.getDateAdd());
                entity.setTotalPaidTaxIncl(dto.getTotalPaidTaxIncl());
                entity.setTotalPaidTaxExcl(dto.getTotalPaidTaxExcl());
                entity.setTotalShippingTaxExcl(dto.getTotalShippingTaxExcl());
                entity.setTotalShippingTaxIncl(dto.getTotalShippingTaxIncl());
                entity.setStatus(dto.getStatus());

                if (dto.getShippingAddress() != null) {
                        ShippingAddressEntity shippingAddress = mapShippingAddress(dto.getShippingAddress());
                        entity.setShippingAddress(shippingAddress);
                }

                if (dto.getProducts() != null) {
                        List<ProductEntity> products = dto.getProducts().stream()
                                        .map(this::createProductEntityForOrder)
                                        .collect(Collectors.toList());
                        entity.setProducts(products);
                }

                if (dto.getCarriers() != null) {
                        List<CarriersEntity> carriers = dto.getCarriers().stream()
                                        .map(carrierDto -> createCarrierEntity(carrierDto.getName()))
                                        .collect(Collectors.toList());
                        entity.setCarriers(carriers);
                }

                return entity;
        }

        @Override
        public OrderResponseDto toResponseDto(OrderEntity entity) {
                if (entity == null) {
                        return null;
                }

                OrderResponseDto dto = new OrderResponseDto();
                dto.setId(entity.getId());
                dto.setInternalReference(entity.getInternalReference());
                dto.setDateAdd(entity.getDateAdd());
                dto.setTotalPaidTaxIncl(entity.getTotalPaidTaxIncl());
                dto.setTotalPaidTaxExcl(entity.getTotalPaidTaxExcl());
                dto.setTotalShippingTaxExcl(entity.getTotalShippingTaxExcl());
                dto.setTotalShippingTaxIncl(entity.getTotalShippingTaxIncl());
                dto.setStatus(entity.getStatus());

                if (entity.getShippingAddress() != null) {
                        dto.setShippingAddress(mapShippingAddressToDto(entity.getShippingAddress()));
                }

                if (entity.getProducts() != null) {
                        List<ProductResponseDto> productDtos = entity.getProducts().stream()
                                        .map(productMapper::toResponseDto)
                                        .collect(Collectors.toList());
                        dto.setProducts(productDtos);
                } else {
                        dto.setProducts(Collections.emptyList());
                }

                if (entity.getCarriers() != null) {
                        List<CarrierResponseDto> carrierDtos = entity.getCarriers().stream()
                                        .map(this::mapCarrierToDto)
                                        .collect(Collectors.toList());
                        dto.setCarriers(carrierDtos);
                } else {
                        dto.setCarriers(Collections.emptyList());
                }

                return dto;
        }

        @Override
        public void updateEntityFromDto(OrderRequestDto dto, OrderEntity entity) {
                if (dto == null || entity == null)
                        return;

                entity.setInternalReference(dto.getInternalReference());
                entity.setTotalPaidTaxIncl(dto.getTotalPaidTaxIncl());
                entity.setTotalPaidTaxExcl(dto.getTotalPaidTaxExcl());
                entity.setTotalShippingTaxExcl(dto.getTotalShippingTaxExcl());
                entity.setTotalShippingTaxIncl(dto.getTotalShippingTaxIncl());
                entity.setStatus(dto.getStatus());
        }

        // ============================
        // Metodi helper
        // ============================

        private ShippingAddressEntity mapShippingAddress(ShippingAddressRequestDto shippingDto) {
                if (shippingDto == null) {
                        return null;
                }

                ShippingAddressEntity shippingAddress = new ShippingAddressEntity();
                shippingAddress.setFirstName(shippingDto.getFirstName());
                shippingAddress.setLastName(shippingDto.getLastName());
                shippingAddress.setCountry(shippingDto.getCountry());
                shippingAddress.setPostcode(shippingDto.getPostcode());
                shippingAddress.setTown(shippingDto.getTown());
                shippingAddress.setAddress(shippingDto.getAddress());
                shippingAddress.setPhone(shippingDto.getPhone());
                shippingAddress.setEmail(shippingDto.getEmail());
                shippingAddress.setVatNumber(shippingDto.getVatNumber());
                shippingAddress.setCompanyName(shippingDto.getCompanyName());
                shippingAddress.setComment(shippingDto.getComment());
                return shippingAddress;
        }

        private ShippingAddressResponseDto mapShippingAddressToDto(ShippingAddressEntity entity) {
                if (entity == null)
                        return null;

                ShippingAddressResponseDto dto = new ShippingAddressResponseDto();
                dto.setFirstName(entity.getFirstName());
                dto.setLastName(entity.getLastName());
                dto.setCountry(entity.getCountry());
                dto.setPostcode(entity.getPostcode());
                dto.setTown(entity.getTown());
                dto.setAddress(entity.getAddress());
                dto.setPhone(entity.getPhone());
                dto.setEmail(entity.getEmail());
                dto.setVatNumber(entity.getVatNumber());
                dto.setCompanyName(entity.getCompanyName());
                dto.setComment(entity.getComment());
                return dto;
        }

        private ProductEntity createProductEntityForOrder(Object productDto) {
                if (productDto == null)
                        return null;

                ProductEntity product = new ProductEntity();

                if (productDto instanceof ProductQuantityDto) {
                        ProductQuantityDto quantityDto = (ProductQuantityDto) productDto;
                        product.setQuantity(quantityDto.getQuantity());
                        if (quantityDto.getReference() != null) {
                                product.setReference(quantityDto.getReference());
                                product.setSku("ORDER-TEMP-" + quantityDto.getReference());
                        } else {
                                product.setReference("PRODUCT-" + quantityDto.getProductId());
                                product.setSku("ORDER-TEMP-" + quantityDto.getProductId());
                        }
                } else if (productDto instanceof ProductRequestDto) {
                        ProductRequestDto requestDto = (ProductRequestDto) productDto;
                        product.setQuantity(requestDto.getQuantity());
                        product.setReference(requestDto.getReference());
                        product.setSku(requestDto.getSku());
                        product.setRetailPrice(requestDto.getRetailPrice());
                } else {
                        product.setReference("UNKNOWN-PRODUCT");
                        product.setSku("ORDER-TEMP-UNKNOWN");
                        product.setQuantity(1);
                }

                if (product.getSku() == null) {
                        product.setSku("ORDER-TEMP-" + System.currentTimeMillis());
                }
                if (product.getRetailPrice() == null) {
                        product.setRetailPrice(java.math.BigDecimal.ZERO);
                }

                product.setActive(true);
                return product;
        }

        private CarriersEntity createCarrierEntity(String name) {
                if (name == null || name.trim().isEmpty()) {
                        return null;
                }

                CarriersEntity carrier = new CarriersEntity();
                carrier.setName(name);
                return carrier;
        }

        private CarrierResponseDto mapCarrierToDto(CarriersEntity entity) {
                if (entity == null) {
                        return null;
                }

                return new CarrierResponseDto(
                                entity.getId(),
                                entity.getName(),
                                entity.getPrice());
        }
}
