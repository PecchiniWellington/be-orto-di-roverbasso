package com.ortoroverbasso.ortorovebasso.mapper.order_custom;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomProductDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomUpdateDto;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartItemEntity;
import com.ortoroverbasso.ortorovebasso.entity.order_custom.OrderCustomEntity;
import com.ortoroverbasso.ortorovebasso.entity.order_custom.OrderCustomProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;
import com.ortoroverbasso.ortorovebasso.enums.StatusOrderEnum;
import com.ortoroverbasso.ortorovebasso.mapper.pickup.PickupMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;

@Component
public class OrderCustomMapper {
    @Autowired
    private ProductRepository productRepository;

    public static OrderCustomResponseDto toDto(OrderCustomEntity entity) {
        if (entity == null)
            return null;

        OrderCustomResponseDto dto = new OrderCustomResponseDto();
        dto.setId(entity.getId());
        dto.setToken(entity.getToken());
        dto.setStatusOrder(entity.getStatusOrder());

        if (entity.getCart() != null) {
            dto.setCartId(entity.getCart().getId());
        }

        if (entity.getPickupOrder() != null) {
            dto.setPickupOrder(PickupMapper.toDto(entity.getPickupOrder()));
        }

        if (entity.getOrderProducts() != null) {
            List<OrderCustomProductDto> productDtos = entity.getOrderProducts().stream().map(orderProduct -> {
                OrderCustomProductDto opDto = new OrderCustomProductDto();
                opDto.setProduct(ProductMapper.toResponseDto(orderProduct.getProduct()));
                opDto.setQuantity(orderProduct.getQuantity());
                return opDto;
            }).collect(Collectors.toList());

            dto.setProducts(productDtos);
        }

        return dto;
    }

    public OrderCustomEntity updateEntityFromDto(OrderCustomUpdateDto dto, OrderCustomEntity entity) {
        if (dto == null || entity == null)
            return entity;

        if (dto.getPickupOrder() != null) {
            entity.setPickupOrder(PickupMapper.toEntity(dto.getPickupOrder()));
        }

        if (dto.getProducts() != null && !dto.getProducts().isEmpty()) {
            List<OrderCustomProductEntity> updatedProducts = dto.getProducts().stream()
                    .map(pq -> {
                        OrderCustomProductEntity op = new OrderCustomProductEntity();
                        op.setOrder(entity);
                        op.setProduct(productRepository.findById(pq.getProductId()).orElse(null));
                        op.setQuantity(pq.getQuantity());
                        return op;
                    })
                    .filter(op -> op.getProduct() != null)
                    .collect(Collectors.toList());

            entity.getOrderProducts().clear();
            for (OrderCustomProductEntity updatedProduct : updatedProducts) {
                updatedProduct.setOrder(entity);
                entity.getOrderProducts().add(updatedProduct);
            }
        }

        return entity;
    }

    public static OrderCustomEntity fromCart(CartEntity cart, List<CartItemEntity> cartItems, PickupEntity pickup) {
        OrderCustomEntity order = new OrderCustomEntity();
        order.setToken(UUID.randomUUID().toString());
        order.setStatusOrder(StatusOrderEnum.PENDING);
        order.setPickupOrder(pickup);
        order.setCart(cart);

        List<OrderCustomProductEntity> orderProducts = cartItems.stream().map(item -> {
            OrderCustomProductEntity op = new OrderCustomProductEntity();
            op.setOrder(order);
            op.setProduct(item.getProduct());
            op.setQuantity(item.getQuantity());
            return op;
        }).collect(Collectors.toList());

        order.setOrderProducts(orderProducts);

        return order;
    }
}
