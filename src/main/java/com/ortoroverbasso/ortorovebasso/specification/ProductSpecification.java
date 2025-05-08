package com.ortoroverbasso.ortorovebasso.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductSpecification {

    public static Specification<ProductEntity> build(ProductFilterRequestDto filter) {
        return (Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // ✅ Filtra per slug di categoria (ManyToOne)
            if (filter.getCategorySlugs() != null && !filter.getCategorySlugs().isEmpty()) {
                predicates.add(root.get("category").get("slug").in(filter.getCategorySlugs()));
            }

            // 🔍 Filtra per stringa di ricerca nel nome

            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
                var infoJoin = root.join("productInformation"); // join con l'entità collegata
                predicates.add(cb.like(cb.lower(infoJoin.get("name")), "%" + filter.getSearch().toLowerCase() + "%"));
            }

            // 💲 Filtra per prezzo minimo
            if (filter.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("retailPrice"), filter.getMinPrice()));
            }

            // 💲 Filtra per prezzo massimo
            if (filter.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("retailPrice"), filter.getMaxPrice()));
            }

            // ✅ Filtra per disponibilità (usando quantità > 0)
            if (filter.getAvailable() != null) {
                if (filter.getAvailable()) {
                    predicates.add(cb.greaterThan(root.get("quantity"), 0));
                } else {
                    predicates.add(cb.lessThanOrEqualTo(root.get("quantity"), 0));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<ProductEntity> hasCategory(CategoryEntity category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    public static Specification<ProductEntity> isActive() {
        return (root, query, cb) -> cb.equal(root.get("active"), 1);
    }

    public static Specification<ProductEntity> fetchProductImages() {
        return (root, query, cb) -> {
            // Questo è importante per evitare duplicati e problemi di fetch multiplo
            root.fetch("productImages", JoinType.LEFT);
            query.distinct(true); // Altrimenti potresti avere prodotti duplicati
            return null;
        };
    }
}
