package com.ortoroverbasso.ortorovebasso.dto.filters.product_filters;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.enums.ProductSortEnum;

public class ProductFilterRequestDto {
    private List<String> categorySlugs;
    private String search;
    private Double minPrice;
    private Double maxPrice;
    private Boolean available;
    private ProductSortEnum sort;

    // Getters e Setters
    public List<String> getCategorySlugs() {
        return categorySlugs;
    }

    public void setCategorySlugs(List<String> categorySlugs) {
        this.categorySlugs = categorySlugs;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public ProductSortEnum getSort() {
        return sort;
    }

    public void setSort(ProductSortEnum sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ProductFilterRequestDto{" +
                "categorySlugs=" + categorySlugs +
                ", search='" + search + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", available=" + available +
                ", sort=" + sort +
                '}';
    }
}
