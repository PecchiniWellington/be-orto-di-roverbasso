package com.ortoroverbasso.ortorovebasso.repository.product.product_information;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_informations.ProductInformationEntity;

@Repository
public interface ProductInformationRepository extends JpaRepository<ProductInformationEntity, Long> {

    ProductInformationEntity findBySku(String sku);

    List<ProductInformationResponseDto> findByProductId(Long productId);

}
