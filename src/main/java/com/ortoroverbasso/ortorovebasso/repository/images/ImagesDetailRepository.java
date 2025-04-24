package com.ortoroverbasso.ortorovebasso.repository.images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.images.ImagesDetailEntity;

@Repository
public interface ImagesDetailRepository extends JpaRepository<ImagesDetailEntity, Long> {

}
