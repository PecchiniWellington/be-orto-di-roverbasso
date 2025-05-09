package com.ortoroverbasso.ortorovebasso.repository.order_custom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.order_custom.OrderCustomProductEntity;

@Repository
public interface OrderCustomProductRepository extends JpaRepository<OrderCustomProductEntity, Long> {
    // puoi aggiungere query personalizzate se servono
}
