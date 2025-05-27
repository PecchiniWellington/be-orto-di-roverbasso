package com.ortoroverbasso.ortorovebasso.repository.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;

// CategoryRepository.java
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);

    CategoryEntity findBySlug(String slug);

    @Query("SELECT DISTINCT c FROM CategoryEntity c " +
            "LEFT JOIN FETCH c.subCategories sc " +
            "LEFT JOIN FETCH c.products p " +
            "LEFT JOIN FETCH p.productInformation " +
            "WHERE c.parentCategory IS NULL")
    List<CategoryEntity> findAllWithSubCategoriesAndProducts();

}
