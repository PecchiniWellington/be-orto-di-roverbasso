package com.ortoroverbasso.ortorovebasso.repository.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;

// CategoryRepository.java
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);

}
