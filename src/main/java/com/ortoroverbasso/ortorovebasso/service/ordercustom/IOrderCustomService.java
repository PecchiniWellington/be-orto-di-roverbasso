package com.ortoroverbasso.ortorovebasso.service.ordercustom;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomResponseDto;

public interface IOrderCustomService {

    OrderCustomResponseDto createOrderCustom(OrderCustomRequestDto orderCustomRequestDto);

    List<OrderCustomResponseDto> getAllOrderCustoms();

    OrderCustomResponseDto getOrderCustomById(Long id);

    OrderCustomResponseDto getOrderCustomByToken(String token);

    OrderCustomResponseDto updateOrderCustom(Long id, OrderCustomRequestDto orderCustomRequestDto);

    void deleteOrderCustom(Long id);
}
