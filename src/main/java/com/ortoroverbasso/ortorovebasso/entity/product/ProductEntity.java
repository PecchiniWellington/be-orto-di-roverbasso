package com.ortoroverbasso.ortorovebasso.entity.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.entity.manufacturer.ManufacturerEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_attributes.ProductAttributesEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_features.ProductFeaturesEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_informations.ProductInformationEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_why_choose.ProductWhyChooseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;

    private String wholesalePrice;
    private Double retailPrice;
    private Long taxonomy;
    private LocalDateTime dateAdd;
    private Integer active;
    private Boolean attributes;
    private Boolean images;
    private Integer taxRate;
    private Integer taxId;
    private Double inShopsPrice;
    private Boolean tags;
    private String reference;
    private Integer quantity;
    private Integer discount;
    private Double weight;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductAttributesEntity> productAttributes;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductLargeQuantityPriceEntity> priceLargeQuantities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    private ManufacturerEntity manufacturer;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImageEntity> productImages = new ArrayList<>();

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    @JoinColumn(name = "product_information_id", referencedColumnName = "id", nullable = true)
    private ProductInformationEntity productInformation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_features_id", referencedColumnName = "id")
    private List<ProductFeaturesEntity> productFeatures = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "product_whychoose", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "whychoose_id"))
    private Set<ProductWhyChooseEntity> whyChoose = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private CategoryEntity category;

    public ProductEntity(String reference,
            Integer quantity) {
        this.reference = reference;
        this.quantity = quantity;
    }

    // All-args constructor
    public ProductEntity() {
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ManufacturerEntity getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerEntity manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(String wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
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

    public Boolean getTags() {
        return tags;
    }

    public void setTags(Boolean tags) {
        this.tags = tags;
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

    public List<ProductImageEntity> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImageEntity> productImages) {
        this.productImages = productImages;
    }

    public ProductInformationEntity getProductInformation() {
        return productInformation;
    }

    public void setProductInformation(ProductInformationEntity productInformation) {
        this.productInformation = productInformation;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public List<ProductFeaturesEntity> getProductFeatures() {
        return productFeatures;
    }

    public void setProductFeatures(List<ProductFeaturesEntity> productFeatures) {
        this.productFeatures = productFeatures;
    }

    public Set<ProductWhyChooseEntity> getWhyChoose() {
        return whyChoose;
    }

    public void setWhyChoose(Set<ProductWhyChooseEntity> whyChoose) {
        this.whyChoose = whyChoose;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
