package com.ortoroverbasso.ortorovebasso.entity.taxonomy;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "taxonomies")
public class TaxonomyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;
    private Long parentTaxonomy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdd;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;

    private String urlImages;
    private String isoCode;

    // Getters and Setters

    public TaxonomyEntity() {
    }

    public TaxonomyEntity(
            Long id,
            String name,
            String url,
            Long parentTaxonomy,
            Date dateAdd,
            Date dateUpd,
            String urlImages,
            String isoCode) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.parentTaxonomy = parentTaxonomy;
        this.dateAdd = dateAdd;
        this.dateUpd = dateUpd;
        this.urlImages = urlImages;
        this.isoCode = isoCode;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentTaxonomy() {
        return parentTaxonomy;
    }

    public void setParentTaxonomy(Long parentTaxonomy) {
        this.parentTaxonomy = parentTaxonomy;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    public String getUrlImages() {
        return urlImages;
    }

    public void setUrlImages(String urlImages) {
        this.urlImages = urlImages;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
}
