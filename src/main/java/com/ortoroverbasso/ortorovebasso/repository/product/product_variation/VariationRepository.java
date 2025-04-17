package com.ortoroverbasso.ortorovebasso.repository.product.product_variation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.product_variation.VariationEntity;

@Repository
public interface VariationRepository extends JpaRepository<VariationEntity, Long> {
    // Puoi aggiungere metodi personalizzati qui se necessario
}
