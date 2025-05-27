package com.ortoroverbasso.ortorovebasso.repository.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductFlatDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

        ProductEntity findBySku(String sku);

        List<ProductEntity> findByCategory(CategoryEntity category);

        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.priceLargeQuantities
                        LEFT JOIN FETCH p.productInformation
                        LEFT JOIN FETCH p.productImages
                        LEFT JOIN FETCH p.category
                        WHERE p.id = :productId
                        """)
        Optional<ProductEntity> findFullProductById(Long productId);

        @Query(value = """
                        SELECT DISTINCT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productImages
                        LEFT JOIN FETCH p.productInformation
                        LEFT JOIN FETCH p.category
                        """, countQuery = "SELECT COUNT(p) FROM ProductEntity p")
        Page<ProductEntity> findAllWithDetails(Pageable pageable);

        @Query("""
                        SELECT new com.ortoroverbasso.ortorovebasso.dto.product.ProductFlatDto(
                            p.id,
                            p.sku,
                            p.reference,
                            img.url,
                            info.name,
                            p.retailPrice
                        )
                        FROM ProductEntity p
                        LEFT JOIN p.productImages img WITH img.isCover = true
                        LEFT JOIN p.productInformation info
                        """)
        List<ProductFlatDto> findAllFlat();
}
