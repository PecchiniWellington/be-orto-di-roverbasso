package com.ortoroverbasso.ortorovebasso.repository.product.product_images;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImageEntity, Long> {

    /**
     * Trova tutte le immagini di un prodotto
     */
    List<ProductImageEntity> findByProductId(Long productId);

    /**
     * Trova un'immagine specifica di un prodotto
     */
    ProductImageEntity findByProductIdAndId(Long productId, Long imageId);

    /**
     * Trova tutte le immagini di un prodotto ordinate per displayOrder
     */
    @Query("SELECT p FROM ProductImageEntity p WHERE p.product.id = :productId ORDER BY p.displayOrder ASC, p.id ASC")
    List<ProductImageEntity> findByProductIdOrderByDisplayOrder(@Param("productId") Long productId);

    /**
     * Trova l'immagine di copertina di un prodotto
     */
    @Query("SELECT p FROM ProductImageEntity p WHERE p.product.id = :productId AND p.isCover = true")
    Optional<ProductImageEntity> findCoverImageByProductId(@Param("productId") Long productId);

    /**
     * Conta il numero di immagini di un prodotto
     */
    long countByProductId(Long productId);

    /**
     * Verifica se un prodotto ha immagini
     */
    boolean existsByProductId(Long productId);

    /**
     * Verifica se un prodotto ha un'immagine di copertina
     */
    @Query("SELECT COUNT(p) > 0 FROM ProductImageEntity p WHERE p.product.id = :productId AND p.isCover = true")
    boolean existsCoverImageByProductId(@Param("productId") Long productId);

    /**
     * Elimina tutte le immagini di un prodotto
     */
    void deleteByProductId(Long productId);

    /**
     * Trova immagini per lista di IDs di prodotti
     */
    @Query("SELECT p FROM ProductImageEntity p WHERE p.product.id IN :productIds ORDER BY p.product.id, p.displayOrder")
    List<ProductImageEntity> findByProductIdIn(@Param("productIds") List<Long> productIds);

    /**
     * Aggiorna lo stato di copertina per tutte le immagini di un prodotto
     */
    @Query("UPDATE ProductImageEntity p SET p.isCover = false WHERE p.product.id = :productId")
    void resetCoverStatusForProduct(@Param("productId") Long productId);

    /**
     * Trova la prima immagine di un prodotto (fallback per copertina)
     */
    @Query("SELECT p FROM ProductImageEntity p WHERE p.product.id = :productId ORDER BY p.displayOrder ASC, p.id ASC")
    Optional<ProductImageEntity> findFirstImageByProductId(@Param("productId") Long productId);

    /**
     * Conta immagini per più prodotti
     */
    @Query("SELECT p.product.id, COUNT(p) FROM ProductImageEntity p WHERE p.product.id IN :productIds GROUP BY p.product.id")
    List<Object[]> countImagesByProductIds(@Param("productIds") List<Long> productIds);

    /**
     * Trova immagini senza prodotto associato (orphaned)
     */
    @Query("SELECT p FROM ProductImageEntity p WHERE p.product IS NULL")
    List<ProductImageEntity> findOrphanedImages();

    /**
     * Trova prodotti che hanno più di N immagini
     */
    @Query("SELECT p.product.id FROM ProductImageEntity p GROUP BY p.product.id HAVING COUNT(p) > :minCount")
    List<Long> findProductIdsWithMoreThanNImages(@Param("minCount") int minCount);

    /**
     * Trova prodotti senza immagini
     */
    @Query("SELECT DISTINCT pr.id FROM ProductEntity pr WHERE pr.id NOT IN (SELECT DISTINCT p.product.id FROM ProductImageEntity p WHERE p.product.id IS NOT NULL)")
    List<Long> findProductIdsWithoutImages();

    /**
     * Trova le ultime N immagini caricate
     */
    @Query("SELECT p FROM ProductImageEntity p ORDER BY p.id DESC")
    List<ProductImageEntity> findLatestImages(org.springframework.data.domain.Pageable pageable);
}
