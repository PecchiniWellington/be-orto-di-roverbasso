package com.ortoroverbasso.ortorovebasso.specification.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Specification per la creazione di query dinamiche sui prodotti
 */
public class ProductSpecification {

    /**
     * Specification per prodotti attivi
     */
    public static Specification<ProductEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("active"));
    }

    /**
     * Specification per categoria
     */
    public static Specification<ProductEntity> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    /**
     * Specification per liste di categorie
     */
    public static Specification<ProductEntity> hasCategoryIn(List<Long> categoryIds) {
        return (root, query, criteriaBuilder) -> {
            if (categoryIds == null || categoryIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("category").get("id").in(categoryIds);
        };
    }

    /**
     * Specification per produttore
     */
    public static Specification<ProductEntity> hasManufacturer(Long manufacturerId) {
        return (root, query, criteriaBuilder) -> {
            if (manufacturerId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("manufacturer").get("id"), manufacturerId);
        };
    }

    /**
     * Specification per range di prezzo
     */
    public static Specification<ProductEntity> priceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("retailPrice"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("retailPrice"), maxPrice));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Specification per ricerca testuale
     */
    public static Specification<ProductEntity> containsText(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(searchTerm)) {
                return criteriaBuilder.conjunction();
            }

            String likePattern = "%" + searchTerm.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("sku")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("reference")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("productInformation").get("name")),
                            likePattern));
        };
    }

    /**
     * Specification per prodotti con sconto
     */
    public static Specification<ProductEntity> hasDiscount() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("discount"), 0);
    }

    /**
     * Specification per prodotti con quantità bassa
     */
    public static Specification<ProductEntity> hasLowStock(Integer threshold) {
        return (root, query, criteriaBuilder) -> {
            final Integer finalThreshold = threshold != null ? threshold : 10; // Default threshold
            return criteriaBuilder.lessThanOrEqualTo(root.get("quantity"), finalThreshold);
        };
    }

    /**
     * Specification per prodotti disponibili
     */
    public static Specification<ProductEntity> isAvailable() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.isTrue(root.get("active")),
                criteriaBuilder.greaterThan(root.get("quantity"), 0));
    }

    /**
     * Specification per peso in range
     */
    public static Specification<ProductEntity> weightInRange(BigDecimal minWeight, BigDecimal maxWeight) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minWeight != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("weight"), minWeight));
            }

            if (maxWeight != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("weight"), maxWeight));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Costruisce una Specification completa dal FilterRequestDto
     */
    public static Specification<ProductEntity> build(ProductFilterRequestDto filter) {
        Specification<ProductEntity> spec = isActive();

        if (filter == null) {
            return spec;
        }

        // Filtro per ricerca testuale
        if (StringUtils.hasText(filter.getSearch())) {
            spec = spec.and(containsText(filter.getSearch()));
        }

        // Filtro per range di prezzo
        if (filter.getMinPrice() != null || filter.getMaxPrice() != null) {
            BigDecimal minPrice = filter.getMinPrice() != null ? BigDecimal.valueOf(filter.getMinPrice()) : null;
            BigDecimal maxPrice = filter.getMaxPrice() != null ? BigDecimal.valueOf(filter.getMaxPrice()) : null;
            spec = spec.and(priceInRange(minPrice, maxPrice));
        }

        // Filtro per disponibilità
        if (filter.getAvailable() != null && filter.getAvailable()) {
            spec = spec.and(isAvailable());
        }

        return spec;
    }

    /**
     * Metodo helper per costruire predicati da ProductFilterRequestDto
     */
    public static List<Predicate> buildPredicateList(ProductFilterRequestDto filter,
            CriteriaBuilder cb,
            Root<ProductEntity> root) {
        List<Predicate> predicates = new ArrayList<>();

        // Sempre attivo
        predicates.add(cb.isTrue(root.get("active")));

        if (filter == null) {
            return predicates;
        }

        // Filtro per range di prezzo
        if (filter.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("retailPrice"), BigDecimal.valueOf(filter.getMinPrice())));
        }

        if (filter.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("retailPrice"), BigDecimal.valueOf(filter.getMaxPrice())));
        }

        // Filtro per ricerca testuale
        if (StringUtils.hasText(filter.getSearch())) {
            String likePattern = "%" + filter.getSearch().toLowerCase() + "%";
            predicates.add(cb.or(
                    cb.like(cb.lower(root.get("sku")), likePattern),
                    cb.like(cb.lower(root.get("reference")), likePattern),
                    cb.like(cb.lower(root.get("productInformation").get("name")), likePattern)));
        }

        // Filtro per disponibilità
        if (filter.getAvailable() != null && filter.getAvailable()) {
            predicates.add(cb.greaterThan(root.get("quantity"), 0));
        }

        return predicates;
    }
}
