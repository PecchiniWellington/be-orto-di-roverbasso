package com.ortoroverbasso.ortorovebasso.repository.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.specification.ProductSpecification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<ProductEntity> findFilteredProducts(ProductFilterRequestDto filter, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Query per i risultati
        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> root = query.from(ProductEntity.class);

        // Join solo per entità necessarie
        root.fetch("productImages", JoinType.LEFT);
        root.fetch("productInformation", JoinType.LEFT);
        root.fetch("category", JoinType.LEFT);
        query.distinct(true);

        List<Predicate> predicates = ProductSpecification.buildPredicateList(filter, cb, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.asc(root.get("id")));

        List<ProductEntity> resultList = em.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Query per il count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ProductEntity> countRoot = countQuery.from(ProductEntity.class);
        List<Predicate> countPredicates = ProductSpecification.buildPredicateList(filter, cb, countRoot);
        countQuery.select(cb.countDistinct(countRoot)).where(countPredicates.toArray(new Predicate[0]));

        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }
}
