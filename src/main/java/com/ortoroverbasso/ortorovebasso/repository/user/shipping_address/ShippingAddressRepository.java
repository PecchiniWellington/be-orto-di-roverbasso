package com.ortoroverbasso.ortorovebasso.repository.user.shipping_address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.user.shipping_address.ShippingAddressEntity;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddressEntity, Long> {

}
