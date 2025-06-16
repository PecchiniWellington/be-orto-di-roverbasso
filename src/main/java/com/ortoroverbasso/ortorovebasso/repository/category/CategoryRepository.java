package com.ortoroverbasso.ortorovebasso.repository.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
        Optional<CategoryEntity> findByName(String name);

        CategoryEntity findBySlug(String slug);

        @Query("SELECT DISTINCT c FROM CategoryEntity c LEFT JOIN FETCH c.subCategories WHERE c.parentCategory IS NULL")
        List<CategoryEntity> findAllWithSubCategories(); // solo subCategories

        @Query("SELECT DISTINCT c FROM CategoryEntity c LEFT JOIN FETCH c.products WHERE c IN :categories")
        List<CategoryEntity> findAllWithProducts(List<CategoryEntity> categories); // solo products

        @Query("""
                        SELECT DISTINCT c FROM CategoryEntity c
                        LEFT JOIN FETCH c.subCategories
                        WHERE c.parentCategory IS NULL
                        """)
        List<CategoryEntity> findRootCategoriesWithSubCategories();

        @Query("""
                            SELECT c FROM CategoryEntity c
                            LEFT JOIN FETCH c.subCategories
                            WHERE c.slug = :slug
                        """)
        CategoryEntity findBySlugWithSubCategories(@Param("slug") String slug);

}
