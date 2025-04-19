package com.ortoroverbasso.ortorovebasso.repository.shipping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.shipping.CarriersEntity;

@Repository
public interface CarriersRepository extends JpaRepository<CarriersEntity, Long> {

}
