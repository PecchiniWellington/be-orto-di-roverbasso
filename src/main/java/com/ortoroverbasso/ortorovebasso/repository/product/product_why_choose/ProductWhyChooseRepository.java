package com.ortoroverbasso.ortorovebasso.repository.product.product_why_choose;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.product.product_why_choose.ProductWhyChooseEntity;

@Repository
public interface ProductWhyChooseRepository extends JpaRepository<ProductWhyChooseEntity, Long> {

    @Query(value = "SELECT p.product_id FROM product_whychoose p WHERE p.whychoose_id = :whyChooseId", nativeQuery = true)
    List<Long> findProductIdsByWhyChooseId(@Param("whyChooseId") Long whyChooseId);

}
