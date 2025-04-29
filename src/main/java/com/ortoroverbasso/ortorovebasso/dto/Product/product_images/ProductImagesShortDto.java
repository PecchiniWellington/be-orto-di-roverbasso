package com.ortoroverbasso.ortorovebasso.dto.product.product_images;

public class ProductImagesShortDto {
    private Long id;
    private String name;
    private String url;
    private boolean isCover;

    public ProductImagesShortDto(Long id, String url, boolean isCover) {

        this.id = id;

        this.url = url;

        this.isCover = isCover;

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

    public boolean isCover() {
        return isCover;
    }

    public void setCover(boolean isCover) {
        this.isCover = isCover;
    }

}
