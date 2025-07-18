package com.ortoroverbasso.ortorovebasso.repository.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.utils.criteria.CriteriaBuilderUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Implementazione custom per ProductRepository usando utilities centralizzate
 */
@Repository
public class ProductRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Implementazione ottimizzata con CriteriaBuilderUtils
     */
    public Page<ProductEntity> findFilteredProducts(ProductFilterRequestDto filter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Query per i risultati
        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> root = query.from(ProductEntity.class);

        // Fetch joins ottimizzati
        root.fetch("productImages", JoinType.LEFT);
        root.fetch("productInformation", JoinType.LEFT);
        root.fetch("category", JoinType.LEFT);
        query.distinct(true);

        // Costruzione predicati usando utilities
        List<Predicate> predicates = buildPredicates(filter, cb, root);
        query.where(CriteriaBuilderUtils.combineWithAnd(cb, predicates));
        query.orderBy(cb.asc(root.get("id")));

        // Query per count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ProductEntity> countRoot = countQuery.from(ProductEntity.class);
        List<Predicate> countPredicates = buildPredicates(filter, cb, countRoot);
        countQuery.select(cb.countDistinct(countRoot))
                .where(CriteriaBuilderUtils.combineWithAnd(cb, countPredicates));

        // Esecuzione usando utility
        return CriteriaBuilderUtils.executePagedQuery(entityManager, query, countQuery, pageable);
    }

    /**
     * Costruisce predicati usando CriteriaBuilderUtils
     */
    private List<Predicate> buildPredicates(ProductFilterRequestDto filter, CriteriaBuilder cb,
            Root<ProductEntity> root) {
        List<Predicate> predicates = new ArrayList<>();

        // Sempre attivo
        predicates.add(CriteriaBuilderUtils.createBooleanPredicate(cb, root, "active", true));

        if (filter == null) {
            return predicates;
        }

        // Range di prezzo
        if (filter.getMinPrice() != null || filter.getMaxPrice() != null) {
            predicates.add(CriteriaBuilderUtils.createRangePredicate(cb, root, "retailPrice",
                    filter.getMinPrice(), filter.getMaxPrice()));
        }

        // Ricerca testuale
        if (filter.getSearch() != null) {
            predicates.add(CriteriaBuilderUtils.createTextSearchPredicate(cb, root, filter.getSearch(),
                    "sku", "reference", "productInformation.name"));
        }

        // Disponibilità
        if (filter.getAvailable() != null && filter.getAvailable()) {
            predicates.add(cb.greaterThan(root.get("quantity"), 0));
        }

        // Categorie (se nel futuro aggiungi il supporto)
        if (filter.getCategorySlugs() != null && !filter.getCategorySlugs().isEmpty()) {
            // Placeholder per future implementazioni
            // predicates.add(CriteriaBuilderUtils.createInPredicate(cb, root,
            // "category.slug", filter.getCategorySlugs()));
        }

        return predicates;
    }
}
