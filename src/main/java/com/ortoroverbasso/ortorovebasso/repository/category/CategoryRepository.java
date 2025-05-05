package com.ortoroverbasso.ortorovebasso.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;

// CategoryRepository.java
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
