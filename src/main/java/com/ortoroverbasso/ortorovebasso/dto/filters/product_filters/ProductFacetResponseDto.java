package com.ortoroverbasso.ortorovebasso.dto.filters.product_filters;

import java.util.List;

public class ProductFacetResponseDto {
    private Double minPrice;
    private Double maxPrice;
    private List<PriceRangeResponseDto> priceRanges;

    private Double minWeight;
    private Double maxWeight;
    private List<WeightRangeResponseDto> weightRanges;

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

    public List<PriceRangeResponseDto> getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(List<PriceRangeResponseDto> priceRanges) {
        this.priceRanges = priceRanges;
    }

    public Double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(Double minWeight) {
        this.minWeight = minWeight;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public List<WeightRangeResponseDto> getWeightRanges() {
        return weightRanges;
    }

    public void setWeightRanges(List<WeightRangeResponseDto> weightRanges) {
        this.weightRanges = weightRanges;
    }
}
