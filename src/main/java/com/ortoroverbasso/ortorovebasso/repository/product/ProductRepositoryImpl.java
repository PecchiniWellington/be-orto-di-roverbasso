package com.ortoroverbasso.ortorovebasso.repository.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.specification.product.ProductSpecification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Implementazione custom per ProductRepository
 * Spring rileva automaticamente questa classe tramite naming convention
 * e la integra con ProductRepository interface
 */
@Repository
public class ProductRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Implementazione custom del metodo findFilteredProducts
     */
    public Page<ProductEntity> findFilteredProducts(ProductFilterRequestDto filter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Query per i risultati
        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> root = query.from(ProductEntity.class);

        // Fetch joins per evitare N+1 queries
        root.fetch("productImages", JoinType.LEFT);
        root.fetch("productInformation", JoinType.LEFT);
        root.fetch("category", JoinType.LEFT);
        query.distinct(true);

        // Costruzione dei predicati usando ProductSpecification
        List<Predicate> predicates = ProductSpecification.buildPredicateList(filter, cb, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.asc(root.get("id")));

        // Esecuzione query con paginazione
        List<ProductEntity> resultList = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Query per il count totale
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ProductEntity> countRoot = countQuery.from(ProductEntity.class);
        List<Predicate> countPredicates = ProductSpecification.buildPredicateList(filter, cb, countRoot);
        countQuery.select(cb.countDistinct(countRoot)).where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }
}
