package com.ortoroverbasso.ortorovebasso.controller.ordercustom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomStatusUpdateDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomUpdateDto;
import com.ortoroverbasso.ortorovebasso.service.order_custom.IOrderCustomService;

@RestController
@RequestMapping("/api/order-custom")
public class OrderCustomController {

    @Autowired
    private IOrderCustomService orderCustomService;

    @PostMapping
    public ResponseEntity<OrderCustomResponseDto> createOrderCustom(
            @RequestBody OrderCustomRequestDto orderCustomRequestDto) {
        OrderCustomResponseDto createdOrderCustom = orderCustomService.createOrderCustom(orderCustomRequestDto);
        return new ResponseEntity<>(createdOrderCustom, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderCustomResponseDto>> getAllOrderCustoms() {
        List<OrderCustomResponseDto> orderCustoms = orderCustomService.getAllOrderCustoms();
        return new ResponseEntity<>(orderCustoms, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderCustomResponseDto>> getAllOrderCustomsByUserId(@PathVariable Long id) {
        List<OrderCustomResponseDto> orderCustoms = orderCustomService.getAllOrderCustomsByUserId(id);
        return new ResponseEntity<>(orderCustoms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderCustomResponseDto> getOrderCustomById(@PathVariable Long id) {
        OrderCustomResponseDto orderCustom = orderCustomService.getOrderCustomById(id);
        return new ResponseEntity<>(orderCustom, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderCustomResponseDto> updateOrderCustom(
            @PathVariable Long id,
            @RequestBody OrderCustomUpdateDto orderCustomUpdateDto) {
        OrderCustomResponseDto updatedOrderCustom = orderCustomService.updateOrderCustom(id, orderCustomUpdateDto);
        return new ResponseEntity<>(updatedOrderCustom, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderCustom(@PathVariable Long id) {
        orderCustomService.deleteOrderCustom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderCustomResponseDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody OrderCustomStatusUpdateDto statusUpdateDto) {

        OrderCustomResponseDto updatedOrder = orderCustomService.updateOrderStatus(
                id,
                statusUpdateDto.getStatusOrder(),
                statusUpdateDto.getUserId());
        return ResponseEntity.ok(updatedOrder);
    }
}
