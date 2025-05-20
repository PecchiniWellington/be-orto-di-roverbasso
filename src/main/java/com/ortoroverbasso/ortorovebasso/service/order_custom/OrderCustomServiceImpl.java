package com.ortoroverbasso.ortorovebasso.service.order_custom;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomUpdateDto;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;
import com.ortoroverbasso.ortorovebasso.entity.cart.CartItemEntity;
import com.ortoroverbasso.ortorovebasso.entity.order_custom.OrderCustomEntity;
import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;
import com.ortoroverbasso.ortorovebasso.entity.user.UserEntity;
import com.ortoroverbasso.ortorovebasso.enums.StatusOrderEnum;
import com.ortoroverbasso.ortorovebasso.exception.ResourceNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.order_custom.OrderCustomMapper;
import com.ortoroverbasso.ortorovebasso.repository.order_custom.OrderCustomRepository;
import com.ortoroverbasso.ortorovebasso.repository.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderCustomServiceImpl implements IOrderCustomService {

    @Autowired
    private OrderCustomRepository orderCustomRepository;
    @Autowired
    private OrderCustomMapper orderCustomMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public OrderCustomResponseDto createOrderCustom(OrderCustomRequestDto orderCustomRequestDto) {
        throw new UnsupportedOperationException("Use createOrderCustomFromCart instead.");
    }

    @Transactional
    @Override
    public OrderCustomResponseDto createOrderCustomFromCart(List<CartItemEntity> cartItems, PickupEntity savedPickup) {
        CartEntity cart = cartItems.stream()
                .findFirst()
                .map(CartItemEntity::getCart)
                .orElseThrow(() -> new RuntimeException("Cart items vuoti o senza riferimento al cart"));

        if (cartItems.isEmpty() || cartItems.get(0).getCart() == null) {
            throw new IllegalArgumentException("Cart items vuoti o senza riferimento al cart");
        }
        OrderCustomEntity order = OrderCustomMapper.fromCart(cart, cartItems, savedPickup);

        order.setCart(cart);
        cart.getOrders().add(order);

        OrderCustomEntity savedOrder = orderCustomRepository.save(order);

        return OrderCustomMapper.toDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderCustomResponseDto> getAllOrderCustoms() {
        return orderCustomRepository.findAllByOrderByIdDesc().stream()
                .map(OrderCustomMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderCustomResponseDto getOrderCustomById(Long id) {
        OrderCustomEntity orderCustom = orderCustomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderCustom not found with id: " + id));
        return OrderCustomMapper.toDto(orderCustom);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderCustomResponseDto getOrderCustomByToken(String token) {
        OrderCustomEntity orderCustom = orderCustomRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("OrderCustom not found with token: " + token));
        return OrderCustomMapper.toDto(orderCustom);
    }

    @Transactional
    @Override
    public OrderCustomResponseDto updateOrderCustom(Long id, OrderCustomUpdateDto dto) {
        OrderCustomEntity existing = orderCustomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        OrderCustomEntity updated = orderCustomMapper.updateEntityFromDto(dto, existing);
        OrderCustomEntity saved = orderCustomRepository.save(updated);

        return OrderCustomMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteOrderCustom(Long id) {
        if (!orderCustomRepository.existsById(id)) {
            throw new ResourceNotFoundException("OrderCustom not found with id: " + id);
        }
        orderCustomRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderCustomEntity saveOrder(OrderCustomEntity order) {
        return orderCustomRepository.save(order);
    }

    @Override
    @Transactional
    public OrderCustomResponseDto updateOrderStatus(Long orderId, StatusOrderEnum statusOrder, Long userId) {
        OrderCustomEntity order = orderCustomRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Ordine non trovato"));

        order.setStatusOrder(statusOrder);

        if (userId != null) {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            order.setUser(user);
        }

        OrderCustomEntity saved = orderCustomRepository.save(order);
        return OrderCustomMapper.toDto(saved);
    }

    @Override
    public List<OrderCustomResponseDto> getAllOrderCustomsByUserId(Long userId) {
        List<OrderCustomEntity> orders = orderCustomRepository.findByUser_Id(userId);

        return orders.stream()
                .map(order -> OrderCustomMapper.toDto(order))
                .collect(Collectors.toList());
    }

}
