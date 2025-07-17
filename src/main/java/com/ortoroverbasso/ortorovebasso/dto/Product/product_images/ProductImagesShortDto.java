package com.ortoroverbasso.ortorovebasso.dto.product.product_images;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO semplificato per le immagini del prodotto")
public class ProductImagesShortDto {

    @Schema(description = "ID dell'immagine", example = "1")
    private Long id;

    @Schema(description = "URL dell'immagine", example = "https://example.com/images/product.jpg")
    private String url;

    @Schema(description = "Indica se questa è l'immagine di copertina", example = "true")
    private Boolean isCover;

    // Constructors
    public ProductImagesShortDto() {
    }

    public ProductImagesShortDto(Long id, String url, Boolean isCover) {
        this.id = id;
        this.url = url;
        this.isCover = isCover;
    }

    // Utility methods
    public boolean isCover() {
        return Boolean.TRUE.equals(isCover);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsCover() {
        return isCover;
    }

    public void setIsCover(Boolean isCover) {
        this.isCover = isCover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ProductImagesShortDto that = (ProductImagesShortDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductImagesShortDto{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", isCover=" + isCover +
                '}';
    }
}
