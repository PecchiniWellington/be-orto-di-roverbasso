package com.ortoroverbasso.ortorovebasso.service.order_custom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.entity.order_custom.OrderCustomProductEntity;
import com.ortoroverbasso.ortorovebasso.repository.order_custom.OrderCustomProductRepository;

@Service
public class OrderCustomProductServiceImpl {

    @Autowired
    private OrderCustomProductRepository orderProductRepository;

    public List<OrderCustomProductEntity> findAll() {
        return orderProductRepository.findAll();
    }

    public OrderCustomProductEntity save(OrderCustomProductEntity entity) {
        return orderProductRepository.save(entity);
    }

    public void deleteById(Long id) {
        orderProductRepository.deleteById(id);
    }

    public OrderCustomProductEntity findById(Long id) {
        return orderProductRepository.findById(id).orElse(null);
    }
}
