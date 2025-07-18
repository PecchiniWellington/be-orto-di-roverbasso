package com.ortoroverbasso.ortorovebasso.service.product.product_filters.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.filters.paginate.PaginatedResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.PriceRangeResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFacetResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.WeightRangeResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.enums.ProductSortEnum;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.service.product.product_filters.IProductFacetService;
import com.ortoroverbasso.ortorovebasso.specification.product.ProductSpecification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ProductFacetServiceImpl implements IProductFacetService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductFacetResponseDto getFacets(ProductFilterRequestDto filterDto) {
        Specification<ProductEntity> spec = ProductSpecification.build(filterDto);
        ProductFacetResponseDto facet = new ProductFacetResponseDto();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // MIN/MAX prezzo
        calculatePriceRange(spec, facet, cb);

        // Fasce di prezzo con conteggio
        if (facet.getMinPrice() != null && facet.getMaxPrice() != null) {
            calculatePriceRanges(spec, facet, cb);
        }

        // MIN/MAX peso
        calculateWeightRange(spec, facet, cb);

        // Fasce di peso con conteggio
        if (facet.getMinWeight() != null && facet.getMaxWeight() != null) {
            calculateWeightRanges(spec, facet, cb);
        }

        return facet;
    }

    private void calculatePriceRange(Specification<ProductEntity> spec, ProductFacetResponseDto facet,
            CriteriaBuilder cb) {
        // MIN prezzo
        CriteriaQuery<BigDecimal> minQuery = cb.createQuery(BigDecimal.class);
        Root<ProductEntity> root = minQuery.from(ProductEntity.class);
        minQuery.select(cb.min(root.get("retailPrice")));
        Predicate predicate = spec.toPredicate(root, minQuery, cb);
        if (predicate != null) {
            minQuery.where(predicate);
        }
        BigDecimal minPrice = entityManager.createQuery(minQuery).getSingleResult();
        facet.setMinPrice(minPrice != null ? minPrice.doubleValue() : null);

        // MAX prezzo
        CriteriaQuery<BigDecimal> maxQuery = cb.createQuery(BigDecimal.class);
        root = maxQuery.from(ProductEntity.class);
        maxQuery.select(cb.max(root.get("retailPrice")));
        predicate = spec.toPredicate(root, maxQuery, cb);
        if (predicate != null) {
            maxQuery.where(predicate);
        }
        BigDecimal maxPrice = entityManager.createQuery(maxQuery).getSingleResult();
        facet.setMaxPrice(maxPrice != null ? maxPrice.doubleValue() : null);
    }

    private void calculatePriceRanges(Specification<ProductEntity> spec, ProductFacetResponseDto facet,
            CriteriaBuilder cb) {
        List<PriceRangeResponseDto> ranges = new ArrayList<>();
        double step = 20;
        double start = Math.floor(facet.getMinPrice() / step) * step;
        double end = Math.ceil(facet.getMaxPrice() / step) * step;

        for (double i = start; i < end; i += step) {
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<ProductEntity> root = countQuery.from(ProductEntity.class);
            Predicate predicate = spec.toPredicate(root, countQuery, cb);
            Predicate rangePredicate = cb.between(
                    root.get("retailPrice"),
                    BigDecimal.valueOf(i),
                    BigDecimal.valueOf(i + step - 1));

            if (predicate != null) {
                countQuery.select(cb.count(root)).where(cb.and(predicate, rangePredicate));
            } else {
                countQuery.select(cb.count(root)).where(rangePredicate);
            }

            Long count = entityManager.createQuery(countQuery).getSingleResult();
            if (count > 0) {
                ranges.add(new PriceRangeResponseDto(i, i + step - 1, count));
            }
        }

        facet.setPriceRanges(ranges);
    }

    private void calculateWeightRange(Specification<ProductEntity> spec, ProductFacetResponseDto facet,
            CriteriaBuilder cb) {
        // MIN peso
        CriteriaQuery<BigDecimal> minQuery = cb.createQuery(BigDecimal.class);
        Root<ProductEntity> root = minQuery.from(ProductEntity.class);
        minQuery.select(cb.min(root.get("weight")));
        Predicate predicate = spec.toPredicate(root, minQuery, cb);
        if (predicate != null) {
            minQuery.where(predicate);
        }
        BigDecimal minWeight = entityManager.createQuery(minQuery).getSingleResult();
        facet.setMinWeight(minWeight != null ? minWeight.doubleValue() : null);

        // MAX peso
        CriteriaQuery<BigDecimal> maxQuery = cb.createQuery(BigDecimal.class);
        root = maxQuery.from(ProductEntity.class);
        maxQuery.select(cb.max(root.get("weight")));
        predicate = spec.toPredicate(root, maxQuery, cb);
        if (predicate != null) {
            maxQuery.where(predicate);
        }
        BigDecimal maxWeight = entityManager.createQuery(maxQuery).getSingleResult();
        facet.setMaxWeight(maxWeight != null ? maxWeight.doubleValue() : null);
    }

    private void calculateWeightRanges(Specification<ProductEntity> spec, ProductFacetResponseDto facet,
            CriteriaBuilder cb) {
        List<WeightRangeResponseDto> ranges = new ArrayList<>();
        double step = 100;
        double start = Math.floor(facet.getMinWeight() / step) * step;
        double end = Math.ceil(facet.getMaxWeight() / step) * step;

        for (double i = start; i < end; i += step) {
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<ProductEntity> root = countQuery.from(ProductEntity.class);
            Predicate predicate = spec.toPredicate(root, countQuery, cb);
            Predicate rangePredicate = cb.between(
                    root.get("weight"),
                    BigDecimal.valueOf(i),
                    BigDecimal.valueOf(i + step - 1));

            if (predicate != null) {
                countQuery.select(cb.count(root)).where(cb.and(predicate, rangePredicate));
            } else {
                countQuery.select(cb.count(root)).where(rangePredicate);
            }

            Long count = entityManager.createQuery(countQuery).getSingleResult();
            if (count > 0) {
                ranges.add(new WeightRangeResponseDto(i, i + step - 1, count));
            }
        }

        facet.setWeightRanges(ranges);
    }

    @Override
    public PaginatedResponseDto<ProductResponseDto> getFilteredProducts(ProductFilterRequestDto filterDto,
            Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Applica sorting se presente
        Pageable sortedPageable = applySorting(pageable, filterDto.getSort());

        // Query per i risultati
        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> root = query.from(ProductEntity.class);

        // Fetch joins per evitare N+1 queries
        root.fetch("productImages", JoinType.LEFT);
        root.fetch("productInformation", JoinType.LEFT);
        root.fetch("category", JoinType.LEFT);
        query.distinct(true);

        // Costruzione predicati
        List<Predicate> predicates = ProductSpecification.buildPredicateList(filterDto, cb, root);
        query.where(predicates.toArray(new Predicate[0]));

        // Applicazione del sorting
        applySortingToQuery(query, cb, root, filterDto.getSort());

        // Esecuzione query con paginazione
        List<ProductEntity> resultList = entityManager.createQuery(query)
                .setFirstResult((int) sortedPageable.getOffset())
                .setMaxResults(sortedPageable.getPageSize())
                .getResultList();

        // Query per il count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ProductEntity> countRoot = countQuery.from(ProductEntity.class);
        List<Predicate> countPredicates = ProductSpecification.buildPredicateList(filterDto, cb, countRoot);
        countQuery.select(cb.countDistinct(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        // Conversione in DTO
        List<ProductResponseDto> dtoList = ProductMapper.toResponseDtoList(resultList);

        return PaginatedResponseDto.fromPage(new PageImpl<>(dtoList, sortedPageable, total));
    }

    private Pageable applySorting(Pageable defaultPageable, ProductSortEnum sortOption) {
        if (sortOption == null) {
            return defaultPageable;
        }

        Sort sort = switch (sortOption) {
            case PRICE_ASC -> Sort.by(Sort.Direction.ASC, "retailPrice");
            case PRICE_DESC -> Sort.by(Sort.Direction.DESC, "retailPrice");
            case NAME_ASC -> Sort.by(Sort.Direction.ASC, "productInformation.name");
            case NAME_DESC -> Sort.by(Sort.Direction.DESC, "productInformation.name");
        };

        return PageRequest.of(defaultPageable.getPageNumber(), defaultPageable.getPageSize(), sort);
    }

    private void applySortingToQuery(CriteriaQuery<ProductEntity> query, CriteriaBuilder cb,
            Root<ProductEntity> root, ProductSortEnum sortOption) {
        if (sortOption == null) {
            query.orderBy(cb.asc(root.get("id")));
            return;
        }

        switch (sortOption) {
            case PRICE_ASC -> query.orderBy(cb.asc(root.get("retailPrice")));
            case PRICE_DESC -> query.orderBy(cb.desc(root.get("retailPrice")));
            case NAME_ASC -> query.orderBy(cb.asc(root.get("productInformation").get("name")));
            case NAME_DESC -> query.orderBy(cb.desc(root.get("productInformation").get("name")));
            default -> query.orderBy(cb.asc(root.get("id")));
        }
    }
}
