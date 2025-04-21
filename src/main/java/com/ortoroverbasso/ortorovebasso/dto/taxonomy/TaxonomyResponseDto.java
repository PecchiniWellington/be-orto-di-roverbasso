package com.ortoroverbasso.ortorovebasso.dto.taxonomy;

public class TaxonomyResponseDto {

    private Long id;
    private String name;
    private String url;
    private Long parentTaxonomy;
    private String dateAdd;
    private String dateUpd;
    private String urlImages;
    private String isoCode;

    // Default constructor
    public TaxonomyResponseDto() {
    }

    // All-args constructor
    public TaxonomyResponseDto(
            Long id,
            String name,
            String url,
            Long parentTaxonomy,
            String dateAdd,
            String dateUpd,
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

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(String dateUpd) {
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
    // Getters and Setters
}
