package com.ortoroverbasso.ortorovebasso.service.orders;

import com.ortoroverbasso.ortorovebasso.dto.orders.OrderRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.orders.OrderResponseDto;

public interface IOrderService {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto getOrderById(Long id);

    void updateOrder(Long id, OrderRequestDto orderRequestDto);

    void deleteOrder(Long id);

}
