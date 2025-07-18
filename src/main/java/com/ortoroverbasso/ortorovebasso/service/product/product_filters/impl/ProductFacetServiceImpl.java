package com.ortoroverbasso.ortorovebasso.service.product.product_filters.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.constants.CacheNames;
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
import com.ortoroverbasso.ortorovebasso.utils.criteria.CriteriaBuilderUtils;
import com.ortoroverbasso.ortorovebasso.utils.pagination.PaginationUtils;

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

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Cacheable(value = CacheNames.PRODUCTS, unless = "#result == null")
    @Transactional(readOnly = true)
    public ProductFacetResponseDto getFacets(ProductFilterRequestDto filterDto) {
        Specification<ProductEntity> spec = ProductSpecification.build(filterDto);
        ProductFacetResponseDto facet = new ProductFacetResponseDto();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        calculatePriceRange(spec, facet, cb);
        if (facet.getMinPrice() != null && facet.getMaxPrice() != null) {
            calculatePriceRanges(spec, facet, cb);
        }

        calculateWeightRange(spec, facet, cb);
        if (facet.getMinWeight() != null && facet.getMaxWeight() != null) {
            calculateWeightRanges(spec, facet, cb);
        }

        return facet;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponseDto<ProductResponseDto> getFilteredProducts(ProductFilterRequestDto filterDto,
            Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        Pageable sortedPageable = applySorting(pageable, filterDto.getSort());

        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> root = query.from(ProductEntity.class);

        root.fetch("productImages", JoinType.LEFT);
        root.fetch("productInformation", JoinType.LEFT);
        root.fetch("category", JoinType.LEFT);
        query.distinct(true);

        List<Predicate> predicates = buildPredicates(filterDto, cb, root);
        query.where(CriteriaBuilderUtils.combineWithAnd(cb, predicates));
        applySortingToQuery(query, cb, root, filterDto.getSort());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ProductEntity> countRoot = countQuery.from(ProductEntity.class);
        List<Predicate> countPredicates = buildPredicates(filterDto, cb, countRoot);
        countQuery.select(cb.countDistinct(countRoot))
                .where(CriteriaBuilderUtils.combineWithAnd(cb, countPredicates));

        Page<ProductEntity> page = CriteriaBuilderUtils.executePagedQuery(entityManager, query, countQuery,
                sortedPageable);
        List<ProductResponseDto> dtoList = productMapper.toResponseDtoList(page.getContent());

        return PaginationUtils.toPaginatedResponse(dtoList, sortedPageable, page.getTotalElements());
    }

    private void calculatePriceRange(Specification<ProductEntity> spec, ProductFacetResponseDto facet,
            CriteriaBuilder cb) {
        CriteriaQuery<BigDecimal> minQuery = cb.createQuery(BigDecimal.class);
        Root<ProductEntity> root = minQuery.from(ProductEntity.class);
        minQuery.select(cb.min(root.get("retailPrice")));
        Predicate predicate = spec.toPredicate(root, minQuery, cb);
        if (predicate != null)
            minQuery.where(predicate);
        BigDecimal minPrice = entityManager.createQuery(minQuery).getSingleResult();
        facet.setMinPrice(minPrice != null ? minPrice.doubleValue() : null);

        CriteriaQuery<BigDecimal> maxQuery = cb.createQuery(BigDecimal.class);
        root = maxQuery.from(ProductEntity.class);
        maxQuery.select(cb.max(root.get("retailPrice")));
        predicate = spec.toPredicate(root, maxQuery, cb);
        if (predicate != null)
            maxQuery.where(predicate);
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
            Predicate rangePredicate = CriteriaBuilderUtils.createRangePredicate(cb, root, "retailPrice",
                    BigDecimal.valueOf(i), BigDecimal.valueOf(i + step - 1));

            List<Predicate> predicates = new ArrayList<>();
            if (predicate != null)
                predicates.add(predicate);
            predicates.add(rangePredicate);

            countQuery.select(cb.count(root)).where(CriteriaBuilderUtils.combineWithAnd(cb, predicates));
            Long count = entityManager.createQuery(countQuery).getSingleResult();
            if (count > 0) {
                ranges.add(new PriceRangeResponseDto(i, i + step - 1, count));
            }
        }

        facet.setPriceRanges(ranges);
    }

    private void calculateWeightRange(Specification<ProductEntity> spec, ProductFacetResponseDto facet,
            CriteriaBuilder cb) {
        CriteriaQuery<BigDecimal> minQuery = cb.createQuery(BigDecimal.class);
        Root<ProductEntity> root = minQuery.from(ProductEntity.class);
        minQuery.select(cb.min(root.get("weight")));
        Predicate predicate = spec.toPredicate(root, minQuery, cb);
        if (predicate != null)
            minQuery.where(predicate);
        BigDecimal minWeight = entityManager.createQuery(minQuery).getSingleResult();
        facet.setMinWeight(minWeight != null ? minWeight.doubleValue() : null);

        CriteriaQuery<BigDecimal> maxQuery = cb.createQuery(BigDecimal.class);
        root = maxQuery.from(ProductEntity.class);
        maxQuery.select(cb.max(root.get("weight")));
        predicate = spec.toPredicate(root, maxQuery, cb);
        if (predicate != null)
            maxQuery.where(predicate);
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
            Predicate rangePredicate = CriteriaBuilderUtils.createRangePredicate(cb, root, "weight",
                    BigDecimal.valueOf(i), BigDecimal.valueOf(i + step - 1));

            List<Predicate> predicates = new ArrayList<>();
            if (predicate != null)
                predicates.add(predicate);
            predicates.add(rangePredicate);

            countQuery.select(cb.count(root)).where(CriteriaBuilderUtils.combineWithAnd(cb, predicates));
            Long count = entityManager.createQuery(countQuery).getSingleResult();
            if (count > 0) {
                ranges.add(new WeightRangeResponseDto(i, i + step - 1, count));
            }
        }

        facet.setWeightRanges(ranges);
    }

    private List<Predicate> buildPredicates(ProductFilterRequestDto filterDto, CriteriaBuilder cb,
            Root<ProductEntity> root) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(CriteriaBuilderUtils.createBooleanPredicate(cb, root, "active", true));

        if (filterDto == null)
            return predicates;

        if (filterDto.getMinPrice() != null || filterDto.getMaxPrice() != null) {
            predicates.add(CriteriaBuilderUtils.createRangePredicate(cb, root, "retailPrice",
                    filterDto.getMinPrice(), filterDto.getMaxPrice()));
        }

        if (filterDto.getSearch() != null) {
            predicates.add(CriteriaBuilderUtils.createTextSearchPredicate(cb, root, filterDto.getSearch(),
                    "sku", "reference", "productInformation.name"));
        }

        if (Boolean.TRUE.equals(filterDto.getAvailable())) {
            predicates.add(cb.greaterThan(root.get("quantity"), 0));
        }

        return predicates;
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
