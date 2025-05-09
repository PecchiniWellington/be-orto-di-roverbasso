package com.ortoroverbasso.ortorovebasso.service.ordercustom;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.order_custom.OrderCustomResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.order_custom.OrderCustomEntity;
import com.ortoroverbasso.ortorovebasso.exception.ResourceNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.order_custom.OrderCustomMapper;
import com.ortoroverbasso.ortorovebasso.repository.OrderCustomRepository;

@Service
public class OrderCustomServiceImpl implements IOrderCustomService {

    @Autowired
    private OrderCustomRepository orderCustomRepository;
    @Autowired
    private OrderCustomMapper orderCustomMapper;

    @Override
    @Transactional
    public OrderCustomResponseDto createOrderCustom(OrderCustomRequestDto orderCustomRequestDto) {
        OrderCustomEntity orderCustom = orderCustomMapper.toEntity(orderCustomRequestDto);
        OrderCustomEntity savedOrderCustom = orderCustomRepository.save(orderCustom);
        return orderCustomMapper.toDto(savedOrderCustom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderCustomResponseDto> getAllOrderCustoms() {
        List<OrderCustomEntity> orderCustoms = orderCustomRepository.findAllByOrderByIdDesc();
        return orderCustoms.stream()
                .map(orderCustomMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderCustomResponseDto getOrderCustomById(Long id) {
        OrderCustomEntity orderCustom = orderCustomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderCustom not found with id: " + id));
        return orderCustomMapper.toDto(orderCustom);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderCustomResponseDto getOrderCustomByToken(String token) {
        OrderCustomEntity orderCustom = orderCustomRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("OrderCustom not found with token: " + token));
        return orderCustomMapper.toDto(orderCustom);
    }

    @Override
    @Transactional
    public OrderCustomResponseDto updateOrderCustom(Long id, OrderCustomRequestDto orderCustomRequestDto) {
        OrderCustomEntity existingOrderCustom = orderCustomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderCustom not found with id: " + id));

        // Update existing order with new values
        OrderCustomEntity updatedOrderCustom = orderCustomMapper.updateEntityFromDto(orderCustomRequestDto,
                existingOrderCustom);
        OrderCustomEntity savedOrderCustom = orderCustomRepository.save(updatedOrderCustom);

        return orderCustomMapper.toDto(savedOrderCustom);
    }

    @Override
    @Transactional
    public void deleteOrderCustom(Long id) {
        if (!orderCustomRepository.existsById(id)) {
            throw new ResourceNotFoundException("OrderCustom not found with id: " + id);
        }
        orderCustomRepository.deleteById(id);
    }
}
