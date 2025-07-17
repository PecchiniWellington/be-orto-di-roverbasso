package com.ortoroverbasso.ortorovebasso.dto.product.product_images;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO per la risposta delle immagini del prodotto")
public class ProductImagesResponseDto {

    @Schema(description = "ID dell'immagine", example = "1")
    private Long id;

    @Schema(description = "ID del prodotto associato", example = "123")
    private Long productId;

    @Schema(description = "URL dell'immagine", example = "https://example.com/images/product.jpg")
    private String url;

    @Schema(description = "Testo alternativo per l'immagine", example = "Immagine del prodotto XYZ")
    private String altText;

    @Schema(description = "Indica se questa è l'immagine di copertina", example = "true")
    private Boolean isCover = false;

    @Schema(description = "Ordine di visualizzazione dell'immagine", example = "1")
    private Integer displayOrder = 0;

    // Constructors
    public ProductImagesResponseDto() {
    }

    public ProductImagesResponseDto(Long id, String url, Boolean isCover) {
        this.id = id;
        this.url = url;
        this.isCover = isCover;
    }

    public ProductImagesResponseDto(Long id, String url, String altText, Boolean isCover, Integer displayOrder) {
        this.id = id;
        this.url = url;
        this.altText = altText;
        this.isCover = isCover;
        this.displayOrder = displayOrder;
    }

    // Utility methods
    public boolean isCover() {
        return Boolean.TRUE.equals(isCover);
    }

    public String getDisplayText() {
        return altText != null && !altText.trim().isEmpty() ? altText : "Immagine prodotto";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public Boolean getCover() {
        return isCover;
    }

    public void setCover(Boolean cover) {
        this.isCover = cover;
    }

    public Boolean getIsCover() {
        return isCover;
    }

    public void setIsCover(Boolean isCover) {
        this.isCover = isCover;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public String toString() {
        return "ProductImagesResponseDto{" +
                "id=" + id +
                ", productId=" + productId +
                ", url='" + url + '\'' +
                ", altText='" + altText + '\'' +
                ", isCover=" + isCover +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
