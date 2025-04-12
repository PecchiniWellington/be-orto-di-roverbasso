package com.ortoroverbasso.ortorovebasso.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long manufacturer;

    private String sku;
    private String ean13;
    private Integer weight;
    private Integer height;
    private Integer width;
    private Integer depth;
    private LocalDateTime dateUpd;
    private Long category;
    private LocalDateTime dateUpdDescription;
    private LocalDateTime dateUpdImages;
    private LocalDateTime dateUpdStock;
    private String wholesalePrice;
    private String retailPrice;
    private Long taxonomy;
    private LocalDateTime dateAdd;
    private String video;
    private Integer active;
    private Boolean attributes;
    private Boolean categories;
    private Boolean images;
    private Integer taxRate;
    private Integer taxId;
    private Double inShopsPrice;
    private String condition;
    private String logisticClass;
    private Boolean tags;
    private LocalDateTime dateUpdProperties;
    private LocalDateTime dateUpdCategories;
    private String intrastat;
    private String partNumber;
    private Double canon;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceLargeQuantity> priceLargeQuantities = new ArrayList<>();

    public Long getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Long manufacturer) {
        this.manufacturer = manufacturer;
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

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public LocalDateTime getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(LocalDateTime dateUpd) {
        this.dateUpd = dateUpd;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public LocalDateTime getDateUpdDescription() {
        return dateUpdDescription;
    }

    public void setDateUpdDescription(LocalDateTime dateUpdDescription) {
        this.dateUpdDescription = dateUpdDescription;
    }

    public LocalDateTime getDateUpdImages() {
        return dateUpdImages;
    }

    public void setDateUpdImages(LocalDateTime dateUpdImages) {
        this.dateUpdImages = dateUpdImages;
    }

    public LocalDateTime getDateUpdStock() {
        return dateUpdStock;
    }

    public void setDateUpdStock(LocalDateTime dateUpdStock) {
        this.dateUpdStock = dateUpdStock;
    }

    public String getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(String wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Long getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(Long taxonomy) {
        this.taxonomy = taxonomy;
    }

    public LocalDateTime getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(LocalDateTime dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Boolean getAttributes() {
        return attributes;
    }

    public void setAttributes(Boolean attributes) {
        this.attributes = attributes;
    }

    public Boolean getCategories() {
        return categories;
    }

    public void setCategories(Boolean categories) {
        this.categories = categories;
    }

    public Boolean getImages() {
        return images;
    }

    public void setImages(Boolean images) {
        this.images = images;
    }

    public Integer getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public Double getInShopsPrice() {
        return inShopsPrice;
    }

    public void setInShopsPrice(Double inShopsPrice) {
        this.inShopsPrice = inShopsPrice;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLogisticClass() {
        return logisticClass;
    }

    public void setLogisticClass(String logisticClass) {
        this.logisticClass = logisticClass;
    }

    public Boolean getTags() {
        return tags;
    }

    public void setTags(Boolean tags) {
        this.tags = tags;
    }

    public LocalDateTime getDateUpdProperties() {
        return dateUpdProperties;
    }

    public void setDateUpdProperties(LocalDateTime dateUpdProperties) {
        this.dateUpdProperties = dateUpdProperties;
    }

    public LocalDateTime getDateUpdCategories() {
        return dateUpdCategories;
    }

    public void setDateUpdCategories(LocalDateTime dateUpdCategories) {
        this.dateUpdCategories = dateUpdCategories;
    }

    public List<PriceLargeQuantity> getPriceLargeQuantities() {
        return priceLargeQuantities;
    }

    public void setPriceLargeQuantities(List<PriceLargeQuantity> priceLargeQuantities) {
        this.priceLargeQuantities = priceLargeQuantities;
    }

    public String getIntrastat() {
        return intrastat;
    }

    public void setIntrastat(String intrastat) {
        this.intrastat = intrastat;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Double getCanon() {
        return canon;
    }

    public void setCanon(Double canon) {
        this.canon = canon;
    }
}
