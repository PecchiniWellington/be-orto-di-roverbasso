package com.ortoroverbasso.ortorovebasso.service.orders.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.orders.OrderRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.orders.OrderResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.order.OrderEntity;
import com.ortoroverbasso.ortorovebasso.mapper.orders.OrderMapper;
import com.ortoroverbasso.ortorovebasso.repository.orders.OrderRepository;
import com.ortoroverbasso.ortorovebasso.service.orders.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        OrderEntity orderEntity = orderMapper.toEntity(orderRequestDto);
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        return orderMapper.toResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toResponseDto(orderEntity);
    }

    @Override
    public void updateOrder(Long id, OrderRequestDto orderRequestDto) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderMapper.updateEntityFromDto(orderRequestDto, orderEntity);
        orderRepository.save(orderEntity);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
