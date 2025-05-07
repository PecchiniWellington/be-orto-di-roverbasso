package com.ortoroverbasso.ortorovebasso.utils;

import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.enums.ProductSortEnum;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

public class ProductSortUtils {

    public static Order getSortOrder(CriteriaBuilder cb, CriteriaQuery<?> query, Root<ProductEntity> root,
            ProductSortEnum sortOption) {
        if (sortOption == null)
            return null;

        return switch (sortOption) {
            case PRICE_ASC -> cb.asc(root.get("retailPrice"));
            case PRICE_DESC -> cb.desc(root.get("retailPrice"));
            case NAME_ASC -> cb.asc(root.join("productInformation").get("name"));
            case NAME_DESC -> cb.desc(root.join("productInformation").get("name"));
        };
    }
}
