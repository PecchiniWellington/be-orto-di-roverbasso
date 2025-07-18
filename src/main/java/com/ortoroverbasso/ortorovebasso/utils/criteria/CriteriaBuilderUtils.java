package com.ortoroverbasso.ortorovebasso.utils.criteria;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Utility per costruire query CriteriaBuilder riutilizzabili
 */
public final class CriteriaBuilderUtils {

    private CriteriaBuilderUtils() {
        // Utility class
    }

    /**
     * Esegue query paginata con count automatico
     */
    public static <T> Page<T> executePagedQuery(EntityManager entityManager,
            CriteriaQuery<T> query,
            CriteriaQuery<Long> countQuery,
            Pageable pageable) {

        // Esegui query principale
        List<T> resultList = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Esegui count query
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }

    /**
     * Aggiunge predicato per ricerca testuale su più campi
     */
    public static Predicate createTextSearchPredicate(CriteriaBuilder cb, Root<?> root,
            String searchTerm, String... fields) {
        if (!StringUtils.hasText(searchTerm) || fields.length == 0) {
            return cb.conjunction();
        }

        String likePattern = "%" + searchTerm.toLowerCase() + "%";
        List<Predicate> searchPredicates = new ArrayList<>();

        for (String field : fields) {
            searchPredicates.add(cb.like(cb.lower(getNestedField(root, field)), likePattern));
        }

        return cb.or(searchPredicates.toArray(new Predicate[0]));
    }

    /**
     * Aggiunge predicato per range di valori
     */
    public static <T extends Comparable<T>> Predicate createRangePredicate(CriteriaBuilder cb,
            Root<?> root,
            String field,
            T minValue,
            T maxValue) {
        List<Predicate> predicates = new ArrayList<>();

        if (minValue != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(field), minValue));
        }

        if (maxValue != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(field), maxValue));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    /**
     * Aggiunge predicato per lista di valori
     */
    public static <T> Predicate createInPredicate(CriteriaBuilder cb, Root<?> root,
            String field, List<T> values) {
        if (values == null || values.isEmpty()) {
            return cb.conjunction();
        }

        return getNestedField(root, field).in(values);
    }

    /**
     * Gestisce campi nested (es: "category.id", "productInformation.name")
     */
    @SuppressWarnings("unchecked")
    private static <T> jakarta.persistence.criteria.Path<T> getNestedField(Root<?> root, String fieldPath) {
        String[] parts = fieldPath.split("\\.");
        jakarta.persistence.criteria.Path<T> path = (jakarta.persistence.criteria.Path<T>) root;

        for (String part : parts) {
            path = path.get(part);
        }

        return path;
    }

    /**
     * Crea predicato per campo booleano
     */
    public static Predicate createBooleanPredicate(CriteriaBuilder cb, Root<?> root,
            String field, Boolean value) {
        if (value == null) {
            return cb.conjunction();
        }

        return value ? cb.isTrue(root.get(field)) : cb.isFalse(root.get(field));
    }

    /**
     * Combina predicati con AND
     */
    public static Predicate combineWithAnd(CriteriaBuilder cb, List<Predicate> predicates) {
        return cb.and(predicates.toArray(new Predicate[0]));
    }

    /**
     * Combina predicati con OR
     */
    public static Predicate combineWithOr(CriteriaBuilder cb, List<Predicate> predicates) {
        return cb.or(predicates.toArray(new Predicate[0]));
    }
}
