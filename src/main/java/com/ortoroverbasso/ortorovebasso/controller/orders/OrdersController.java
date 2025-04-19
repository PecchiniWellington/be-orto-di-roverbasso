package com.ortoroverbasso.ortorovebasso.controller.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.orders.OrderRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.orders.OrderResponseDto;
import com.ortoroverbasso.ortorovebasso.service.shipping.IShippingServiceService;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private IShippingServiceService shippingService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto createdOrder = shippingService.createOrder(orderRequestDto);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto orderResponseDto = shippingService.getOrderById(id);
        return ResponseEntity.ok(orderResponseDto);
    }
}
