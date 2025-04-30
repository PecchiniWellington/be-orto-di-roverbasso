package com.ortoroverbasso.ortorovebasso.repository.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortoroverbasso.ortorovebasso.entity.cart.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByCartToken(String cartToken);

    Optional<CartEntity> findByUserId(Long userId);
}
