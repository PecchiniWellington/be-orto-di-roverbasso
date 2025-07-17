package com.ortoroverbasso.ortorovebasso.mapper.orders;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;

public class OrderMapper {

        /**
         * Converte OrderRequestDto in OrderEntity
         */
        public static OrderEntity toEntity(OrderRequestDto dto) {
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

                // Mapping Shipping Address - usa mapper dedicato se esiste
                if (dto.getShippingAddress() != null) {
                        ShippingAddressEntity shippingAddress = mapShippingAddress(dto.getShippingAddress());
                        entity.setShippingAddress(shippingAddress);
                }

                // Mapping Products - NOTA: Questo approccio potrebbe non essere corretto
                // In un vero scenario, dovresti recuperare i ProductEntity esistenti dal
                // database
                // invece di crearne di nuovi con solo reference e quantity
                if (dto.getProducts() != null) {
                        List<ProductEntity> products = dto.getProducts().stream()
                                        .map(OrderMapper::createProductEntityForOrder)
                                        .collect(Collectors.toList());
                        entity.setProducts(products);
                }

                // Mapping Carriers
                if (dto.getCarriers() != null) {
                        List<CarriersEntity> carriers = dto.getCarriers().stream()
                                        .map(carrierDto -> createCarrierEntity(carrierDto.getName()))
                                        .collect(Collectors.toList());
                        entity.setCarriers(carriers);
                }

                return entity;
        }

        /**
         * Converte OrderEntity in OrderResponseDto
         */
        public static OrderResponseDto toResponse(OrderEntity entity) {
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

                // Mapping Shipping Address
                if (entity.getShippingAddress() != null) {
                        ShippingAddressResponseDto shippingAddressDto = mapShippingAddressToDto(
                                        entity.getShippingAddress());
                        dto.setShippingAddress(shippingAddressDto);
                }

                // Mapping Products - usa ProductMapper se disponibile
                if (entity.getProducts() != null) {
                        List<ProductResponseDto> productDtos = entity.getProducts().stream()
                                        .map(ProductMapper::toResponseDto)
                                        .collect(Collectors.toList());
                        dto.setProducts(productDtos);
                } else {
                        dto.setProducts(Collections.emptyList());
                }

                // Mapping Carriers
                if (entity.getCarriers() != null) {
                        List<CarrierResponseDto> carrierDtos = entity.getCarriers().stream()
                                        .map(OrderMapper::mapCarrierToDto)
                                        .collect(Collectors.toList());
                        dto.setCarriers(carrierDtos);
                } else {
                        dto.setCarriers(Collections.emptyList());
                }

                return dto;
        }

        /**
         * Converte una lista di OrderEntity in lista di OrderResponseDto
         */
        public static List<OrderResponseDto> toResponseList(List<OrderEntity> entities) {
                if (entities == null || entities.isEmpty()) {
                        return Collections.emptyList();
                }

                return entities.stream()
                                .map(OrderMapper::toResponse)
                                .collect(Collectors.toList());
        }

        // Metodi helper privati

        private static ShippingAddressEntity mapShippingAddress(
                        com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressRequestDto shippingDto) {

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

        private static ShippingAddressResponseDto mapShippingAddressToDto(ShippingAddressEntity entity) {
                if (entity == null) {
                        return null;
                }

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

        /**
         * ATTENZIONE: Questo metodo crea un ProductEntity minimale per l'ordine.
         * In un vero scenario, dovresti recuperare l'entità dal database
         * usando il ProductRepository.findByReference() o simile.
         */
        private static ProductEntity createProductEntityForOrder(Object productDto) {
                if (productDto == null) {
                        return null;
                }

                ProductEntity product = new ProductEntity();

                // Gestisci diversi tipi di DTO prodotto
                if (productDto instanceof com.ortoroverbasso.ortorovebasso.dto.product.ProductQuantityDto) {
                        com.ortoroverbasso.ortorovebasso.dto.product.ProductQuantityDto quantityDto = (com.ortoroverbasso.ortorovebasso.dto.product.ProductQuantityDto) productDto;

                        product.setQuantity(quantityDto.getQuantity());
                        if (quantityDto.getReference() != null) {
                                product.setReference(quantityDto.getReference());
                                product.setSku("ORDER-TEMP-" + quantityDto.getReference());
                        } else {
                                product.setReference("PRODUCT-" + quantityDto.getProductId());
                                product.setSku("ORDER-TEMP-" + quantityDto.getProductId());
                        }

                } else if (productDto instanceof com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto) {
                        com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto requestDto = (com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto) productDto;

                        product.setQuantity(requestDto.getQuantity());
                        product.setReference(requestDto.getReference());
                        product.setSku(requestDto.getSku());
                        product.setRetailPrice(requestDto.getRetailPrice());

                } else {
                        // Fallback per tipi sconosciuti
                        product.setReference("UNKNOWN-PRODUCT");
                        product.setSku("ORDER-TEMP-UNKNOWN");
                        product.setQuantity(1);
                }

                // Imposta valori di default per campi obbligatori
                if (product.getSku() == null) {
                        product.setSku("ORDER-TEMP-" + System.currentTimeMillis());
                }
                if (product.getRetailPrice() == null) {
                        product.setRetailPrice(java.math.BigDecimal.ZERO);
                }
                product.setActive(true);

                return product;
        }

        private static CarriersEntity createCarrierEntity(String name) {
                if (name == null || name.trim().isEmpty()) {
                        return null;
                }

                CarriersEntity carrier = new CarriersEntity();
                carrier.setName(name);
                // Imposta altri campi di default se necessario

                return carrier;
        }

        private static CarrierResponseDto mapCarrierToDto(CarriersEntity entity) {
                if (entity == null) {
                        return null;
                }

                return new CarrierResponseDto(
                                entity.getId(),
                                entity.getName(),
                                entity.getPrice());
        }

        /**
         * Metodo alternativo più sicuro per mappare prodotti in ordini.
         * Questo dovrebbe essere usato quando hai accesso al ProductRepository.
         */
        public static OrderEntity toEntityWithProductLookup(
                        OrderRequestDto dto,
                        java.util.function.Function<Long, ProductEntity> productFinderById,
                        java.util.function.Function<String, ProductEntity> productFinderByReference) {

                if (dto == null) {
                        return null;
                }

                OrderEntity entity = toEntity(dto);

                // Recupera i veri prodotti dal DB
                if (dto.getProducts() != null) {
                        List<ProductEntity> realProducts = dto.getProducts().stream()
                                        .map(quantityDto -> {
                                                ProductEntity realProduct = null;

                                                if (quantityDto.getProductId() != null && productFinderById != null) {
                                                        realProduct = productFinderById
                                                                        .apply(quantityDto.getProductId());
                                                } else if (quantityDto.getReference() != null
                                                                && productFinderByReference != null) {
                                                        realProduct = productFinderByReference
                                                                        .apply(quantityDto.getReference());
                                                }

                                                if (realProduct != null) {
                                                        ProductEntity orderProduct = new ProductEntity();
                                                        orderProduct.setId(realProduct.getId());
                                                        orderProduct.setSku(realProduct.getSku());
                                                        orderProduct.setReference(realProduct.getReference());
                                                        orderProduct.setRetailPrice(realProduct.getRetailPrice());
                                                        orderProduct.setActive(realProduct.getActive());
                                                        orderProduct.setQuantity(quantityDto.getQuantity());
                                                        return orderProduct;
                                                }

                                                return null;
                                        })
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toList());

                        entity.setProducts(realProducts);
                }

                return entity;
        }

}
