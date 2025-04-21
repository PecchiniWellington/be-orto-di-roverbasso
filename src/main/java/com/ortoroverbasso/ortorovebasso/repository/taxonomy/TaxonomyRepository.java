package com.ortoroverbasso.ortorovebasso.repository.taxonomy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ortoroverbasso.ortorovebasso.entity.taxonomy.TaxonomyEntity;

public interface TaxonomyRepository extends JpaRepository<TaxonomyEntity, Long> {
}
