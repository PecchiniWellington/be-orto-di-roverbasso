package com.ortoroverbasso.ortorovebasso.repository.images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.entity.images.ImagesEntity;

@Repository
public interface ImagesRepository extends JpaRepository<ImagesEntity, Long> {

}
