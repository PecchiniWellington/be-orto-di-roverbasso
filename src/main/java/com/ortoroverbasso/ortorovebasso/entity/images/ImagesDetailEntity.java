package com.ortoroverbasso.ortorovebasso.entity.images;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "images_detail")
public class ImagesDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_cover")
    private Boolean isCover = false;

    private String name;

    @NotBlank(message = "URL is required")
    private String url;

    @Column(name = "is_logo")
    private Boolean isLogo = false;

    @Column(name = "white_background")
    private Boolean whiteBackground = false;

    @PositiveOrZero(message = "Position must be positive or zero")
    private Integer position = 0;

    @Column(name = "energy_efficiency")
    private String energyEfficiency;

    private String icon;

    @Column(name = "marketing_photo")
    private String marketingPhoto;

    @Column(name = "packaging_photo")
    private String packagingPhoto;

    private String brand;

    @Column(name = "gpsr_label")
    private Boolean gpsrLabel = false;

    @Column(name = "gpsr_warning")
    private Boolean gpsrWarning = false;

    @Column(name = "file_id")
    private String fileId;

    // Default constructor
    public ImagesDetailEntity() {
    }

    // Constructor with essential fields
    public ImagesDetailEntity(String name, String url, Boolean isCover) {
        this.name = name;
        this.url = url;
        this.isCover = isCover;
    }

    // All-args constructor
    public ImagesDetailEntity(
            Long id,
            Boolean isCover,
            String name,
            String url,
            Boolean isLogo,
            Boolean whiteBackground,
            Integer position,
            String energyEfficiency,
            String icon,
            String marketingPhoto,
            String packagingPhoto,
            String brand,
            Boolean gpsrLabel,
            Boolean gpsrWarning,
            String fileId) {
        this.id = id;
        this.isCover = isCover;
        this.name = name;
        this.url = url;
        this.isLogo = isLogo;
        this.whiteBackground = whiteBackground;
        this.position = position;
        this.energyEfficiency = energyEfficiency;
        this.icon = icon;
        this.marketingPhoto = marketingPhoto;
        this.packagingPhoto = packagingPhoto;
        this.brand = brand;
        this.gpsrLabel = gpsrLabel;
        this.gpsrWarning = gpsrWarning;
        this.fileId = fileId;
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

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCover() {
        return isCover;
    }

    public void setIsCover(Boolean isCover) {
        this.isCover = isCover;
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

    public Boolean getIsLogo() {
        return isLogo;
    }

    public void setIsLogo(Boolean isLogo) {
        this.isLogo = isLogo;
    }

    // Metodo legacy per compatibilità
    public void setLogo(Boolean logo) {
        this.isLogo = logo;
    }

    public Boolean getWhiteBackground() {
        return whiteBackground;
    }

    public void setWhiteBackground(Boolean whiteBackground) {
        this.whiteBackground = whiteBackground;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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

    public String getPackagingPhoto() {
        return packagingPhoto;
    }

    public void setPackagingPhoto(String packagingPhoto) {
        this.packagingPhoto = packagingPhoto;
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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ImagesDetailEntity that = (ImagesDetailEntity) o;

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
        return "ImagesDetailEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", isCover=" + isCover +
                ", position=" + position +
                '}';
    }
}
