package com.ortoroverbasso.ortorovebasso.repository.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortoroverbasso.ortorovebasso.entity.cart.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByCartId(Long cartId);
}
