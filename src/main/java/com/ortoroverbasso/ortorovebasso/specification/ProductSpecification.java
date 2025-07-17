package com.ortoroverbasso.ortorovebasso.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductSpecification {

    public static List<Predicate> buildPredicateList(ProductFilterRequestDto filter, CriteriaBuilder cb,
            Root<ProductEntity> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getCategorySlugs() != null && !filter.getCategorySlugs().isEmpty()) {
            predicates.add(root.get("category").get("slug").in(filter.getCategorySlugs()));
        }

        if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
            Join<Object, Object> infoJoin = root.join("productInformation", JoinType.LEFT);
            predicates.add(cb.like(cb.lower(infoJoin.get("name")), "%" + filter.getSearch().toLowerCase() + "%"));
        }

        if (filter.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("retailPrice"), filter.getMinPrice()));
        }

        if (filter.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("retailPrice"), filter.getMaxPrice()));
        }

        if (filter.getAvailable() != null) {
            if (filter.getAvailable()) {
                predicates.add(cb.greaterThan(root.get("quantity"), 0));
            } else {
                predicates.add(cb.lessThanOrEqualTo(root.get("quantity"), 0));
            }
        }

        predicates.add(cb.equal(root.get("active"), true));

        return predicates;
    }

    public static Specification<ProductEntity> build(ProductFilterRequestDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = buildPredicateList(filter, cb, root);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
