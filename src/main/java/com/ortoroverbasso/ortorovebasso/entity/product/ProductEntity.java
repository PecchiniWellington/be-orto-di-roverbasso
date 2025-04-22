package com.ortoroverbasso.ortorovebasso.entity.product;

import java.time.LocalDateTime;
import java.util.List;

import com.ortoroverbasso.ortorovebasso.entity.product.product_attributes.ProductAttributesEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductEntity {

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
    private String reference;
    private Integer quantity;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductAttributesEntity> productAttributes;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductLargeQuantityPriceEntity> priceLargeQuantities;

    // Default constructor
    public ProductEntity() {
    }

    public ProductEntity(String reference,
            Integer quantity) {
        this.reference = reference;
        this.quantity = quantity;

    }

    // All-args constructor
    public ProductEntity(
            Long id,
            Long manufacturer,
            String sku,
            String ean13,
            Integer weight,
            Integer height,
            Integer width,
            Integer depth,
            LocalDateTime dateUpd,
            Long category,
            LocalDateTime dateUpdDescription,
            LocalDateTime dateUpdImages,
            LocalDateTime dateUpdStock,
            String wholesalePrice,
            String retailPrice,
            Long taxonomy,
            LocalDateTime dateAdd,
            String video,
            Integer active,
            Boolean attributes,
            Boolean categories,
            Boolean images,
            Integer taxRate,
            Integer taxId,
            Double inShopsPrice,
            String condition,
            String logisticClass,
            Boolean tags,
            LocalDateTime dateUpdProperties,
            LocalDateTime dateUpdCategories,
            String intrastat,
            String partNumber,
            Double canon,
            List<ProductLargeQuantityPriceEntity> priceLargeQuantities,
            String reference,
            Integer quantity) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.sku = sku;
        this.ean13 = ean13;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.dateUpd = dateUpd;
        this.category = category;
        this.dateUpdDescription = dateUpdDescription;
        this.dateUpdImages = dateUpdImages;
        this.dateUpdStock = dateUpdStock;
        this.wholesalePrice = wholesalePrice;
        this.retailPrice = retailPrice;
        this.taxonomy = taxonomy;
        this.dateAdd = dateAdd;
        this.video = video;
        this.active = active;
        this.attributes = attributes;
        this.categories = categories;
        this.images = images;
        this.taxRate = taxRate;
        this.taxId = taxId;
        this.inShopsPrice = inShopsPrice;
        this.condition = condition;
        this.logisticClass = logisticClass;
        this.tags = tags;
        this.dateUpdProperties = dateUpdProperties;
        this.dateUpdCategories = dateUpdCategories;
        this.intrastat = intrastat;
        this.partNumber = partNumber;
        this.canon = canon;
        this.reference = reference;
        this.quantity = quantity;
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Long manufacturer) {
        this.manufacturer = manufacturer;
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

    public List<ProductAttributesEntity> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttributesEntity> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<ProductLargeQuantityPriceEntity> getPriceLargeQuantities() {
        return priceLargeQuantities;
    }

    public void setPriceLargeQuantities(List<ProductLargeQuantityPriceEntity> priceLargeQuantities) {
        this.priceLargeQuantities = priceLargeQuantities;
    }

}
