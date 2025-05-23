package com.ortoroverbasso.ortorovebasso.repository.order_custom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.order_custom.OrderCustomEntity;

@Repository
public interface OrderCustomRepository extends JpaRepository<OrderCustomEntity, Long> {
    Optional<OrderCustomEntity> findByToken(String token);

    List<OrderCustomEntity> findAllByOrderByIdDesc();

    List<OrderCustomEntity> findByUser_Id(Long userId);

}
