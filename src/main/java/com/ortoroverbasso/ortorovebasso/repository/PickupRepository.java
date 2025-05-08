package com.ortoroverbasso.ortorovebasso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.pickup.PickupEntity;

@Repository
public interface PickupRepository extends JpaRepository<PickupEntity, Long> {
    // Qui puoi aggiungere metodi personalizzati se necessario
}
