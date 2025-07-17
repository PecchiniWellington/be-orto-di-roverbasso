package com.ortoroverbasso.ortorovebasso.dto.images;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO per i dettagli delle immagini")
public class ImagesDetailDto {

    @Schema(description = "ID dell'immagine", example = "1")
    private Long id;

    @Schema(description = "Nome dell'immagine", example = "product-main-image")
    private String name;

    @NotBlank(message = "URL dell'immagine è obbligatorio")
    @Schema(description = "URL dell'immagine", example = "https://example.com/images/product.jpg")
    private String url;

    @Schema(description = "Indica se questa è l'immagine di copertina", example = "true")
    private Boolean isCover = false;

    @Schema(description = "Posizione/ordine di visualizzazione", example = "1")
    private Integer position = 0;

    @Schema(description = "Testo alternativo per accessibilità", example = "Immagine principale del prodotto")
    private String altText;

    @Schema(description = "ID del file", example = "file-123")
    private String fileId;

    // Campi legacy mantenuti per compatibilità
    @Schema(description = "Indica se è un logo", example = "false")
    private Boolean isLogo = false;

    @Schema(description = "Indica se ha sfondo bianco", example = "true")
    private Boolean whiteBackground = false;

    @Schema(description = "Efficienza energetica", example = "A+")
    private String energyEfficiency;

    @Schema(description = "Icona associata", example = "energy-star")
    private String icon;

    @Schema(description = "Foto marketing", example = "main-campaign")
    private String marketingPhoto;

    @Schema(description = "Foto packaging", example = "box-image")
    private String packagePhoto;

    @Schema(description = "Brand associato", example = "Samsung")
    private String brand;

    @Schema(description = "Etichetta GPSR", example = "false")
    private Boolean gpsrLabel = false;

    @Schema(description = "Avviso GPSR", example = "false")
    private Boolean gpsrWarning = false;

    // Constructors
    public ImagesDetailDto() {
    }

    public ImagesDetailDto(String name, String url, Boolean isCover) {
        this.name = name;
        this.url = url;
        this.isCover = isCover;
    }

    public ImagesDetailDto(String name, String url, Boolean isCover, Integer position, String altText) {
        this.name = name;
        this.url = url;
        this.isCover = isCover;
        this.position = position;
        this.altText = altText;
    }

    // Utility methods
    public boolean isCover() {
        return Boolean.TRUE.equals(isCover);
    }

    public boolean isLogo() {
        return Boolean.TRUE.equals(isLogo);
    }

    public boolean isWhiteBackground() {
        return Boolean.TRUE.equals(whiteBackground);
    }

    public boolean isGpsrLabel() {
        return Boolean.TRUE.equals(gpsrLabel);
    }

    public boolean isGpsrWarning() {
        return Boolean.TRUE.equals(gpsrWarning);
    }

    // Metodi legacy per compatibilità
    public String getPackagingPhoto() {
        return packagePhoto;
    }

    public void setPackagingPhoto(String packagePhoto) {
        this.packagePhoto = packagePhoto;
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

    public Boolean getIsCover() {
        return isCover;
    }

    public void setIsCover(Boolean isCover) {
        this.isCover = isCover;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Boolean getIsLogo() {
        return isLogo;
    }

    public void setIsLogo(Boolean isLogo) {
        this.isLogo = isLogo;
    }

    public Boolean getWhiteBackground() {
        return whiteBackground;
    }

    public void setWhiteBackground(Boolean whiteBackground) {
        this.whiteBackground = whiteBackground;
    }

    public String getEnergyEfficiency() {
        return energyEfficiency;
    }

    public void setEnergyEfficiency(String energyEfficiency) {
        this.energyEfficiency = energyEfficiency;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMarketingPhoto() {
        return marketingPhoto;
    }

    public void setMarketingPhoto(String marketingPhoto) {
        this.marketingPhoto = marketingPhoto;
    }

    public String getPackagePhoto() {
        return packagePhoto;
    }

    public void setPackagePhoto(String packagePhoto) {
        this.packagePhoto = packagePhoto;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getGpsrLabel() {
        return gpsrLabel;
    }

    public void setGpsrLabel(Boolean gpsrLabel) {
        this.gpsrLabel = gpsrLabel;
    }

    public Boolean getGpsrWarning() {
        return gpsrWarning;
    }

    public void setGpsrWarning(Boolean gpsrWarning) {
        this.gpsrWarning = gpsrWarning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ImagesDetailDto that = (ImagesDetailDto) o;

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
        return "ImagesDetailDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", isCover=" + isCover +
                ", position=" + position +
                ", altText='" + altText + '\'' +
                ", fileId='" + fileId + '\'' +
                '}';
    }
}
