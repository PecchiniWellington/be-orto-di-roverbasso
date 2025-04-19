package com.ortoroverbasso.ortorovebasso.repository.shipping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.shipping.ShippingCostEntity;

@Repository
public interface ShippingCostRepository extends JpaRepository<ShippingCostEntity, Long> {

}
