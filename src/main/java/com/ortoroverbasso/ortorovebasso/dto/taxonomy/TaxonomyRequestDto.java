package com.ortoroverbasso.ortorovebasso.dto.taxonomy;

public class TaxonomyRequestDto {

    private String name;
    private String url;
    private Long parentTaxonomy;
    private String urlImages;
    private String isoCode;

    // Default constructor
    public TaxonomyRequestDto() {
    }

    // All-args constructor
    public TaxonomyRequestDto(
            String name,
            String url,
            Long parentTaxonomy,
            String urlImages,
            String isoCode) {
        this.name = name;
        this.url = url;
        this.parentTaxonomy = parentTaxonomy;
        this.urlImages = urlImages;
        this.isoCode = isoCode;
    }

    // Getters and Setters
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
