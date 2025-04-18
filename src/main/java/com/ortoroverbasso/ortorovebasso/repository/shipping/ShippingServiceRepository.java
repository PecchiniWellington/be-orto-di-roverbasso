package com.ortoroverbasso.ortorovebasso.repository.shipping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.shipping.ShippingServiceEntity;

@Repository
public interface ShippingServiceRepository extends JpaRepository<ShippingServiceEntity, Long> {

}
