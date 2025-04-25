package com.ortoroverbasso.ortorovebasso.entity.images;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "images_detail")
public class ImagesDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isCover;
    private String name;
    private String url;
    private boolean logo;
    private boolean whiteBackground;
    private int position;
    private int energyEfficiency;
    private int icon;
    private int marketingPhoto;
    private int packagingPhoto;
    private int brand;
    private boolean gpsrLabel;
    private boolean gpsrWarning;
    @Column(name = "file_id")
    private String fileId;

    // Default constructor
    public ImagesDetailEntity() {
    }

    // All-args constructor
    public ImagesDetailEntity(
            long id,
            boolean isCover,
            String name,
            String url,
            boolean logo,
            boolean whiteBackground,
            int position,
            int energyEfficiency,
            int icon,
            int marketingPhoto,
            int packagingPhoto,
            int brand,
            boolean gpsrLabel,
            boolean gpsrWarning,
            String fileId) {
        this.id = id;
        this.isCover = isCover;
        this.name = name;
        this.url = url;
        this.logo = logo;
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

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCover() {
        return isCover;
    }

    public void setIsCover(boolean isCover) {
        isCover = isCover;
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

    public boolean isLogo() {
        return logo;
    }

    public void setLogo(boolean logo) {
        this.logo = logo;
    }

    public boolean isWhiteBackground() {
        return whiteBackground;
    }

    public void setWhiteBackground(boolean whiteBackground) {
        this.whiteBackground = whiteBackground;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getEnergyEfficiency() {
        return energyEfficiency;
    }

    public void setEnergyEfficiency(int energyEfficiency) {
        this.energyEfficiency = energyEfficiency;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getMarketingPhoto() {
        return marketingPhoto;
    }

    public void setMarketingPhoto(int marketingPhoto) {
        this.marketingPhoto = marketingPhoto;
    }

    public int getPackagingPhoto() {
        return packagingPhoto;
    }

    public void setPackagingPhoto(int packagingPhoto) {
        this.packagingPhoto = packagingPhoto;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public boolean isGpsrLabel() {
        return gpsrLabel;
    }

    public void setGpsrLabel(boolean gpsrLabel) {
        this.gpsrLabel = gpsrLabel;
    }

    public boolean isGpsrWarning() {
        return gpsrWarning;
    }

    public void setGpsrWarning(boolean gpsrWarning) {
        this.gpsrWarning = gpsrWarning;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

}
