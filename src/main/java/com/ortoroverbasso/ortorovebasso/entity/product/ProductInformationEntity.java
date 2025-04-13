package com.ortoroverbasso.ortorovebasso.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "product_information")
@Entity
public class ProductInformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String name;
    private String description;
    private String url;
    private String isoCode;
    private String dateUpdDescription;

    public ProductInformationEntity() {
    }

    public ProductInformationEntity(Long id, String sku, String name, String description, String url, String isoCode,
            String dateUpdDescription) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.url = url;
        this.isoCode = isoCode;
        this.dateUpdDescription = dateUpdDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getDateUpdDescription() {
        return dateUpdDescription;
    }

    public void setDateUpdDescription(String dateUpdDescription) {
        this.dateUpdDescription = dateUpdDescription;
    }
}
