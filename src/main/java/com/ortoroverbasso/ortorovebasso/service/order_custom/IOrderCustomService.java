package com.ortoroverbasso.ortorovebasso.service.order_custom;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomUpdateDto;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartItemEntity;
import com.ortoroverbasso.ortorovebasso.entity.order_custom.OrderCustomEntity;
import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;
import com.ortoroverbasso.ortorovebasso.enums.StatusOrderEnum;

public interface IOrderCustomService {

    OrderCustomResponseDto createOrderCustom(OrderCustomRequestDto orderCustomRequestDto);

    List<OrderCustomResponseDto> getAllOrderCustoms();

    OrderCustomResponseDto getOrderCustomById(Long id);

    OrderCustomResponseDto getOrderCustomByToken(String token);

    OrderCustomResponseDto updateOrderCustom(Long id, OrderCustomUpdateDto dto);

    void deleteOrderCustom(Long id);

    OrderCustomResponseDto createOrderCustomFromCart(List<CartItemEntity> cartItems, PickupEntity savedPickup);

    OrderCustomEntity saveOrder(OrderCustomEntity order);

    OrderCustomResponseDto updateOrderStatus(Long id, StatusOrderEnum statusOrder);

}
