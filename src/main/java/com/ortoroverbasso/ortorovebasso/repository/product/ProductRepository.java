package com.ortoroverbasso.ortorovebasso.repository.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductFlatDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity>,
                ProductRepositoryCustom {

        /**
         * Trova un prodotto per SKU
         */
        Optional<ProductEntity> findBySku(String sku);

        /**
         * Verifica se esiste un prodotto con il dato SKU
         */
        boolean existsBySku(String sku);

        /**
         * Trova tutti i prodotti di una categoria
         */
        List<ProductEntity> findByCategory(CategoryEntity category);

        /**
         * Trova tutti i prodotti per ID categoria
         */
        @Query("SELECT p FROM ProductEntity p WHERE p.category.id = :categoryId")
        List<ProductEntity> findByCategoryId(@Param("categoryId") Long categoryId);

        /**
         * Trova un prodotto con tutti i dettagli caricati
         */
        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.priceLargeQuantities
                        LEFT JOIN FETCH p.productInformation
                        LEFT JOIN FETCH p.productImages
                        LEFT JOIN FETCH p.category
                        LEFT JOIN FETCH p.manufacturer
                        LEFT JOIN FETCH p.productFeatures
                        LEFT JOIN FETCH p.whyChoose
                        WHERE p.id = :productId
                        """)
        Optional<ProductEntity> findFullProductById(@Param("productId") Long productId);

        /**
         * Trova tutti i prodotti con dettagli essenziali (paginato)
         */
        @Query(value = """
                        SELECT DISTINCT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productImages img
                        LEFT JOIN FETCH p.productInformation info
                        LEFT JOIN FETCH p.category c
                        WHERE p.active = true
                        """, countQuery = "SELECT COUNT(p) FROM ProductEntity p WHERE p.active = true")
        Page<ProductEntity> findAllWithDetails(Pageable pageable);

        /**
         * Trova prodotti attivi con dettagli essenziali
         */
        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productImages
                        LEFT JOIN FETCH p.productInformation
                        LEFT JOIN FETCH p.category
                        WHERE p.active = true
                        """)
        List<ProductEntity> findAllActiveWithDetails();

        /**
         * Query per ProductFlatDto (versione semplificata)
         */
        /**
         * Query per ProductFlatDto (versione semplificata)
         */
        @Query("""
                            SELECT new com.ortoroverbasso.ortorovebasso.dto.product.ProductFlatDto(
                                p.id AS id,
                                p.sku AS sku,
                                p.reference AS reference,
                                COALESCE(img.url, '') AS imageUrl,
                                COALESCE(info.name, '') AS name,
                                p.retailPrice AS retailPrice
                            )
                            FROM ProductEntity p
                            LEFT JOIN p.productImages img WITH img.isCover = true
                            LEFT JOIN p.productInformation info
                            WHERE p.active = true
                            ORDER BY p.id
                        """)
        List<ProductFlatDto> findAllFlat();

        /**
         * Query per ProductFlatDto paginata
         */
        @Query(value = """
                            SELECT new com.ortoroverbasso.ortorovebasso.dto.product.ProductFlatDto(
                                p.id AS id,
                                p.sku AS sku,
                                p.reference AS reference,
                                COALESCE(img.url, '') AS imageUrl,
                                COALESCE(info.name, '') AS name,
                                p.retailPrice AS retailPrice
                            )
                            FROM ProductEntity p
                            LEFT JOIN p.productImages img WITH img.isCover = true
                            LEFT JOIN p.productInformation info
                            WHERE p.active = true
                            ORDER BY p.id
                        """, countQuery = "SELECT COUNT(p) FROM ProductEntity p WHERE p.active = true")
        Page<ProductFlatDto> findAllFlatPaginated(Pageable pageable);

        /**
         * Trova prodotti per range di prezzo
         */
        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productImages
                        LEFT JOIN FETCH p.productInformation
                        LEFT JOIN FETCH p.category
                        WHERE p.active = true
                        AND p.retailPrice BETWEEN :minPrice AND :maxPrice
                        ORDER BY p.retailPrice ASC
                        """)
        Page<ProductEntity> findByRetailPriceBetween(
                        @Param("minPrice") Double minPrice,
                        @Param("maxPrice") Double maxPrice,
                        Pageable pageable);

        /**
         * Trova prodotti per categoria con fetch ottimizzato
         */
        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productImages
                        LEFT JOIN FETCH p.productInformation
                        WHERE p.category.id = :categoryId AND p.active = true
                        ORDER BY p.id
                        """)
        List<ProductEntity> findActiveByCategoryIdWithDetails(@Param("categoryId") Long categoryId);

        /**
         * Trova prodotti per lista di IDs con prezzi
         */
        @Query("""
                        SELECT DISTINCT p FROM ProductEntity p
                        LEFT JOIN FETCH p.priceLargeQuantities
                        WHERE p.id IN :ids
                        """)
        List<ProductEntity> findAllWithPriceLargeQuantitiesByIds(@Param("ids") List<Long> ids);

        /**
         * Conta prodotti attivi
         */
        long countByActiveTrue();

        /**
         * Conta prodotti per categoria
         */
        @Query("SELECT COUNT(p) FROM ProductEntity p WHERE p.category.id = :categoryId AND p.active = true")
        long countActiveByCategoryId(@Param("categoryId") Long categoryId);

        /**
         * Trova prodotti con quantità bassa
         */
        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productInformation
                        WHERE p.active = true AND p.quantity <= :threshold
                        ORDER BY p.quantity ASC
                        """)
        List<ProductEntity> findLowStockProducts(@Param("threshold") Integer threshold);

        /**
         * Trova prodotti con sconto
         */
        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productImages
                        LEFT JOIN FETCH p.productInformation
                        WHERE p.active = true AND p.discount > 0
                        ORDER BY p.discount DESC
                        """)
        Page<ProductEntity> findDiscountedProducts(Pageable pageable);

        /**
         * Cerca prodotti per nome o SKU
         */
        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productInformation info
                        LEFT JOIN FETCH p.productImages
                        WHERE p.active = true AND (
                            LOWER(p.sku) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
                            LOWER(info.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
                            LOWER(p.reference) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
                        )
                        ORDER BY p.id
                        """)
        Page<ProductEntity> searchProducts(@Param("searchTerm") String searchTerm, Pageable pageable);

        /**
         * Trova prodotti per produttore
         */
        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productImages
                        LEFT JOIN FETCH p.productInformation
                        WHERE p.manufacturer.id = :manufacturerId AND p.active = true
                        ORDER BY p.id
                        """)
        List<ProductEntity> findByManufacturerIdWithDetails(@Param("manufacturerId") Long manufacturerId);

        /**
         * Trova prodotti correlati per categoria (escludendo il prodotto corrente)
         */
        @Query("""
                            SELECT p FROM ProductEntity p
                            LEFT JOIN FETCH p.productImages img
                            LEFT JOIN FETCH p.productInformation
                            WHERE p.category.id = :categoryId
                            AND p.id != :excludeProductId
                            AND p.active = true
                            ORDER BY p.id
                        """)
        List<ProductEntity> findRelatedProducts(
                        @Param("categoryId") Long categoryId,
                        @Param("excludeProductId") Long excludeProductId,
                        Pageable pageable);

        /**
         * Statistiche sui prodotti
         */
        @Query("""
                        SELECT
                            COUNT(p) as totalProducts,
                            COUNT(CASE WHEN p.active = true THEN 1 END) as activeProducts,
                            COUNT(CASE WHEN p.quantity <= 10 THEN 1 END) as lowStockProducts,
                            AVG(p.retailPrice) as averagePrice
                        FROM ProductEntity p
                        """)
        Object[] getProductStatistics();

        /**
         * Trova gli ultimi prodotti aggiunti
         */
        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productImages
                        LEFT JOIN FETCH p.productInformation
                        WHERE p.active = true
                        ORDER BY p.dateAdd DESC
                        """)
        List<ProductEntity> findLatestProducts(Pageable pageable);

        @Query("""
                        SELECT p FROM ProductEntity p
                        LEFT JOIN FETCH p.productImages
                        LEFT JOIN FETCH p.productInformation
                        LEFT JOIN FETCH p.category
                        WHERE p.category.id IN :categoryIds AND p.active = true
                        """)
        List<ProductEntity> findAllActiveByCategoryIdsWithDetails(@Param("categoryIds") List<Long> categoryIds);
}
