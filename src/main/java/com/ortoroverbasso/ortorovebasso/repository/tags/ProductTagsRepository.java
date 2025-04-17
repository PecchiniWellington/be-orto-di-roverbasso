package com.ortoroverbasso.ortorovebasso.repository.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.tags.ProductTagsEntity;

@Repository
public interface ProductTagsRepository extends JpaRepository<ProductTagsEntity, Long> {

}
