package com.ortoroverbasso.ortorovebasso.repository.tags;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.tags.ProductTagsEntity;

@Repository
public interface ProductTagsRepository extends JpaRepository<ProductTagsEntity, Long> {
    Optional<ProductTagsEntity> findByProductAndTagId(ProductEntity product, Long tagId);

    boolean existsByProductId(Long productId);

}
