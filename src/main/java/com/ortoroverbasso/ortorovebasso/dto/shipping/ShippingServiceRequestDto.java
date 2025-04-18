package com.ortoroverbasso.ortorovebasso.dto.shipping;

import java.util.List;

public class ShippingServiceRequestDto {
    private Long id;
    private String name;
    private String delay;
    private Integer pod;
    private List<ShippingCountryRequestDto> shippingCountries;
    private List<List<ExcludedProductReferenceRequestDto>> excludedProductReferences;
    private List<List<ExcludedCategoryIdsRequestDto>> excludedCategoryIds;

    // Default constructor
    public ShippingServiceRequestDto() {
    }

    // All-args constructor
    public ShippingServiceRequestDto(
            Long id,
            String name,
            String delay,
            Integer pod,
            List<ShippingCountryRequestDto> shippingCountries,
            List<List<ExcludedProductReferenceRequestDto>> excludedProductReferences,
            List<List<ExcludedCategoryIdsRequestDto>> excludedCategoryIds) {
        this.id = id;
        this.name = name;
        this.delay = delay;
        this.pod = pod;
        this.shippingCountries = shippingCountries;
        this.excludedProductReferences = excludedProductReferences;
        this.excludedCategoryIds = excludedCategoryIds;
    }

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

    public List<ShippingCountryRequestDto> getShippingCountries() {
        return shippingCountries;
    }

    public void setShippingCountries(List<ShippingCountryRequestDto> shippingCountries) {
        this.shippingCountries = shippingCountries;
    }

    public List<List<ExcludedProductReferenceRequestDto>> getExcludedProductReferences() {
        return excludedProductReferences;
    }

    public void setExcludedProductReferences(List<List<ExcludedProductReferenceRequestDto>> excludedProductReferences) {
        this.excludedProductReferences = excludedProductReferences;
    }

    public List<List<ExcludedCategoryIdsRequestDto>> getExcludedCategoryIds() {
        return excludedCategoryIds;
    }

    public void setExcludedCategoryIds(List<List<ExcludedCategoryIdsRequestDto>> excludedCategoryIds) {
        this.excludedCategoryIds = excludedCategoryIds;
    }
}
