package com.ortoroverbasso.ortorovebasso.service.product.product_filters.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.ortoroverbasso.ortorovebasso.specification.ProductSpecification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
        {
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

        // Fasce di prezzo con conteggio
        if (facet.getMinPrice() != null && facet.getMaxPrice() != null) {
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
                countQuery.select(cb.count(root)).where(cb.and(predicate, rangePredicate));
                Long count = entityManager.createQuery(countQuery).getSingleResult();
                ranges.add(new PriceRangeResponseDto(i, i + step - 1, count));
            }

            facet.setPriceRanges(ranges);
        }

        // MIN/MAX peso
        {
            CriteriaQuery<BigDecimal> minW = cb.createQuery(BigDecimal.class);
            Root<ProductEntity> root = minW.from(ProductEntity.class);
            minW.select(cb.min(root.get("weight")));
            Predicate pred = spec.toPredicate(root, minW, cb);
            if (pred != null)
                minW.where(pred);
            BigDecimal minWeight = entityManager.createQuery(minW).getSingleResult();
            facet.setMinWeight(minWeight != null ? minWeight.doubleValue() : null);

            CriteriaQuery<BigDecimal> maxW = cb.createQuery(BigDecimal.class);
            root = maxW.from(ProductEntity.class);
            maxW.select(cb.max(root.get("weight")));
            pred = spec.toPredicate(root, maxW, cb);
            if (pred != null)
                maxW.where(pred);
            BigDecimal maxWeight = entityManager.createQuery(maxW).getSingleResult();
            facet.setMaxWeight(maxWeight != null ? maxWeight.doubleValue() : null);
        }

        // Fasce di peso con conteggio
        if (facet.getMinWeight() != null && facet.getMaxWeight() != null) {
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
                countQuery.select(cb.count(root)).where(cb.and(predicate, rangePredicate));
                Long count = entityManager.createQuery(countQuery).getSingleResult();
                ranges.add(new WeightRangeResponseDto(i, i + step - 1, count));
            }

            facet.setWeightRanges(ranges);
        }

        return facet;
    }

    private Pageable applySorting(Pageable defaultPageable, ProductSortEnum sortOption) {
        if (sortOption == null)
            return defaultPageable;

        Sort sort = switch (sortOption) {
            case PRICE_ASC -> Sort.by(Sort.Direction.ASC, "retailPrice");
            case PRICE_DESC -> Sort.by(Sort.Direction.DESC, "retailPrice");
            case NAME_ASC -> Sort.by(Sort.Direction.ASC, "productInformation.name");
            case NAME_DESC -> Sort.by(Sort.Direction.DESC, "productInformation.name");
        };

        return PageRequest.of(defaultPageable.getPageNumber(), defaultPageable.getPageSize(), sort);
    }

    @Override
    public PaginatedResponseDto<ProductResponseDto> getFilteredProducts(ProductFilterRequestDto filterDto,
            Pageable pageable) {
        Specification<ProductEntity> spec = ProductSpecification.build(filterDto);
        Pageable sortedPageable = applySorting(pageable, filterDto.getSort());
        Page<ProductEntity> productPage = productRepository.findAll(spec, sortedPageable);
        Page<ProductResponseDto> dtoPage = productPage.map(ProductMapper::toResponseDto);
        return PaginatedResponseDto.fromPage(dtoPage);
    }
}
