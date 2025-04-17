package com.ortoroverbasso.ortorovebasso.repository.manufacturer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.manufacturer.ManufacturerEntity;

@Repository
public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {
    List<ManufacturerEntity> findAll();

}
