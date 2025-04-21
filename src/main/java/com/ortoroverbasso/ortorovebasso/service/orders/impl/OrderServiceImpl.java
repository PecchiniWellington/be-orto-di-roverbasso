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

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        // Mappiamo il DTO nella Entity
        OrderEntity orderEntity = OrderMapper.toEntity(orderRequestDto);

        // Salviamo l'ordine nel database
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // Ritorniamo il DTO della risposta
        return OrderMapper.toResponse(savedOrder);
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        // Recuperiamo l'ordine dal database
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Mappiamo l'entità alla risposta DTO
        return OrderMapper.toResponse(orderEntity);
    }

    @Override
    public void updateOrder(Long id, OrderRequestDto orderRequestDto) {
        // Recuperiamo l'ordine dal database
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Aggiorniamo l'ordine con i dati dal DTO
        orderEntity.setInternalReference(orderRequestDto.getInternalReference());
        orderEntity.setDateAdd(orderRequestDto.getDateAdd());
        orderEntity.setTotalPaidTaxIncl(orderRequestDto.getTotalPaidTaxIncl());
        orderEntity.setTotalPaidTaxExcl(orderRequestDto.getTotalPaidTaxExcl());
        orderEntity.setTotalShippingTaxExcl(orderRequestDto.getTotalShippingTaxExcl());
        orderEntity.setTotalShippingTaxIncl(orderRequestDto.getTotalShippingTaxIncl());
        orderEntity.setStatus(orderRequestDto.getStatus());

        // Salviamo i cambiamenti nel database
        orderRepository.save(orderEntity);
    }

    @Override
    public void deleteOrder(Long id) {
        // Eliminiamo l'ordine dal database
        orderRepository.deleteById(id);
    }
}
