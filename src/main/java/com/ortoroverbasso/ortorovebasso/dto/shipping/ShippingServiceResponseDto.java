package com.ortoroverbasso.ortorovebasso.dto.shipping;

import java.util.List;

public class ShippingServiceResponseDto {
    private Long id;
    private String name;
    private String delay;
    private Integer pod;
    private List<ShippingCountryResponseDto> shippingCountries;
    private List<List<ExcludedProductReferenceResponseDto>> excludedProductReferences;
    private List<List<ExcludedCategoryIdsResponseDto>> excludedCategoryIds;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public Integer getPod() {
        return pod;
    }

    public void setPod(Integer pod) {
        this.pod = pod;
    }

    public List<ShippingCountryResponseDto> getShippingCountries() {
        return shippingCountries;
    }

    public void setShippingCountries(List<ShippingCountryResponseDto> shippingCountries) {
        this.shippingCountries = shippingCountries;
    }

    public List<List<ExcludedProductReferenceResponseDto>> getExcludedProductReferences() {
        return excludedProductReferences;
    }

    public void setExcludedProductReferences(
            List<List<ExcludedProductReferenceResponseDto>> excludedProductReferences) {
        this.excludedProductReferences = excludedProductReferences;
    }

    public List<List<ExcludedCategoryIdsResponseDto>> getExcludedCategoryIds() {
        return excludedCategoryIds;
    }

    public void setExcludedCategoryIds(
            List<List<ExcludedCategoryIdsResponseDto>> excludedCategoryIds) {
        this.excludedCategoryIds = excludedCategoryIds;
    }
}
