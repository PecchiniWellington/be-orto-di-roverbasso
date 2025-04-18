package com.ortoroverbasso.ortorovebasso.entity.shipping;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipping_services")
public class ShippingServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String delay;

    private Integer pod;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_service_id")
    private List<ShippingCountryEntity> shippingCountries;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_service_id")
    private List<ExcludedProductReferenceEntity> excludedProductReferences;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_service_id")
    private List<ExcludedCategoryIdsEntity> excludedCategoryIds;

    // Default constructor
    public ShippingServiceEntity() {
    }

    // Constructor with parameters
    public ShippingServiceEntity(
            String name,
            String delay,
            Integer pod) {
        this.name = name;
        this.delay = delay;
        this.pod = pod;
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

    public List<ShippingCountryEntity> getShippingCountries() {
        return shippingCountries;
    }

    public void setShippingCountries(List<ShippingCountryEntity> shippingCountries) {
        this.shippingCountries = shippingCountries;
    }

    public List<ExcludedProductReferenceEntity> getExcludedProductReferences() {
        return excludedProductReferences;
    }

    public void setExcludedProductReferences(List<ExcludedProductReferenceEntity> excludedProductReferences) {
        this.excludedProductReferences = excludedProductReferences;
    }

    public List<ExcludedCategoryIdsEntity> getExcludedCategoryIds() {
        return excludedCategoryIds;
    }

    public void setExcludedCategoryIds(List<ExcludedCategoryIdsEntity> excludedCategoryIds) {
        this.excludedCategoryIds = excludedCategoryIds;
    }
}
