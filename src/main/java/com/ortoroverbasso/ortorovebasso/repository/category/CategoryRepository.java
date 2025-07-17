package com.ortoroverbasso.ortorovebasso.repository.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

        Optional<CategoryEntity> findByName(String name);

        Optional<CategoryEntity> findBySlug(String slug);

        boolean existsBySlug(String slug);

        boolean existsBySlugAndIdNot(String slug, Long id);

        @Query("""
                            SELECT DISTINCT c FROM CategoryEntity c
                            LEFT JOIN FETCH c.subCategories sc
                            LEFT JOIN FETCH c.products p
                            LEFT JOIN FETCH p.productInformation
                            WHERE c.parentCategory IS NULL
                            ORDER BY c.name
                        """)
        List<CategoryEntity> findRootCategoriesWithFullHierarchy();

        @Query("""
                            SELECT c FROM CategoryEntity c
                            LEFT JOIN FETCH c.subCategories
                            LEFT JOIN FETCH c.products p
                            LEFT JOIN FETCH p.productInformation
                            WHERE c.id = :id
                        """)
        Optional<CategoryEntity> findByIdWithDetails(@Param("id") Long id);

        @Query("""
                            SELECT c FROM CategoryEntity c
                            LEFT JOIN FETCH c.subCategories
                            LEFT JOIN FETCH c.products p
                            LEFT JOIN FETCH p.productInformation
                            WHERE c.slug = :slug
                        """)
        Optional<CategoryEntity> findBySlugWithDetails(@Param("slug") String slug);

        @Query("""
                            SELECT c FROM CategoryEntity c
                            LEFT JOIN FETCH c.subCategories
                            WHERE c.slug = :slug
                        """)
        Optional<CategoryEntity> findBySlugWithSubCategories(@Param("slug") String slug);

        @Query("""
                            SELECT c FROM CategoryEntity c
                            WHERE c.parentCategory.id = :parentId
                            ORDER BY c.name
                        """)
        List<CategoryEntity> findByParentCategoryId(@Param("parentId") Long parentId);

        @Query("""
                            SELECT c FROM CategoryEntity c
                            WHERE c.parentCategory IS NULL
                            ORDER BY c.name
                        """)
        List<CategoryEntity> findRootCategories();

        @Query("""
                            SELECT c FROM CategoryEntity c
                            WHERE c.id = :categoryId OR c.parentCategory.id = :categoryId
                            ORDER BY c.name
                        """)
        List<CategoryEntity> findCategoryWithDirectChildren(@Param("categoryId") Long categoryId);

        @Query("""
                            SELECT COUNT(c) FROM CategoryEntity c
                            WHERE c.parentCategory.id = :categoryId
                        """)
        long countSubCategories(@Param("categoryId") Long categoryId);

        @Query("""
                            SELECT COUNT(p) FROM ProductEntity p
                            WHERE p.category.id = :categoryId
                        """)
        long countProducts(@Param("categoryId") Long categoryId);
}
