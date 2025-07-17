package com.ortoroverbasso.ortorovebasso.entity.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "SKU is required")
    @Column(unique = true, nullable = false)
    private String sku;

    @DecimalMin(value = "0.0", message = "Wholesale price must be positive")
    @Column(name = "wholesale_price", precision = 10, scale = 2)
    private BigDecimal wholesalePrice;

    @NotNull(message = "Retail price is required")
    @DecimalMin(value = "0.0", message = "Retail price must be positive")
    @Column(name = "retail_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal retailPrice;

    @Column(name = "taxonomy")
    private Long taxonomy;

    @Column(name = "date_add")
    private LocalDateTime dateAdd;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;

    @NotNull(message = "Active status is required")
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "has_attributes")
    private Boolean hasAttributes = false;

    @Column(name = "has_images")
    private Boolean hasImages = false;

    @Column(name = "tax_rate")
    private Integer taxRate;

    @Column(name = "tax_id")
    private Integer taxId;

    @DecimalMin(value = "0.0", message = "In shops price must be positive")
    @Column(name = "in_shops_price", precision = 10, scale = 2)
    private BigDecimal inShopsPrice;

    @Column(name = "has_tags")
    private Boolean hasTags = false;

    @Column(name = "reference")
    private String reference;

    @PositiveOrZero(message = "Quantity must be positive or zero")
    @Column(name = "quantity")
    private Integer quantity = 0;

    @PositiveOrZero(message = "Discount must be positive or zero")
    @Column(name = "discount")
    private Integer discount = 0;

    @PositiveOrZero(message = "Weight must be positive or zero")
    @Column(name = "weight", precision = 8, scale = 3)
    private BigDecimal weight;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAttributesEntity> productAttributes = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductLargeQuantityPriceEntity> priceLargeQuantities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> productImages = new ArrayList<>();

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductInformationEntity productInformation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_feature", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<ProductFeaturesEntity> productFeatures = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_whychoose", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "whychoose_id"))
    private Set<ProductWhyChooseEntity> whyChoose = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (dateAdd == null) {
            dateAdd = now;
        }
        dateUpdate = now;
        updateDerivedFields();
    }

    @PreUpdate
    protected void onUpdate() {
        dateUpdate = LocalDateTime.now();
        updateDerivedFields();
    }

    /**
     * Aggiorna i campi derivati automaticamente
     */
    private void updateDerivedFields() {
        this.hasImages = (productImages != null && !productImages.isEmpty());
        this.hasAttributes = (productAttributes != null && !productAttributes.isEmpty());
    }

    // Constructors
    public ProductEntity() {
    }

    public ProductEntity(String sku, BigDecimal retailPrice, Boolean active) {
        this.sku = sku;
        this.retailPrice = retailPrice;
        this.active = active;
    }

    // Utility methods
    public boolean isActive() {
        return Boolean.TRUE.equals(active);
    }

    public boolean hasImages() {
        return productImages != null && !productImages.isEmpty();
    }

    public boolean hasAttributes() {
        return productAttributes != null && !productAttributes.isEmpty();
    }

    public boolean hasTags() {
        return Boolean.TRUE.equals(hasTags);
    }

    public boolean isOnSale() {
        return discount != null && discount > 0;
    }

    public boolean isLowStock() {
        return quantity != null && quantity <= 10; // Soglia configurabile
    }

    /**
     * Aggiunge un'immagine e aggiorna la relazione bidirezionale
     */
    public void addImage(ProductImageEntity image) {
        if (image != null) {
            productImages.add(image);
            image.setProduct(this);
            updateDerivedFields();
        }
    }

    /**
     * Rimuove un'immagine e aggiorna la relazione bidirezionale
     */
    public void removeImage(ProductImageEntity image) {
        if (image != null) {
            productImages.remove(image);
            image.setProduct(null);
            updateDerivedFields();
        }
    }

    /**
     * Aggiunge un prezzo per grandi quantità
     */
    public void addLargeQuantityPrice(ProductLargeQuantityPriceEntity price) {
        if (price != null) {
            priceLargeQuantities.add(price);
            price.setProduct(this);
        }
    }

    /**
     * Rimuove un prezzo per grandi quantità
     */
    public void removeLargeQuantityPrice(ProductLargeQuantityPriceEntity price) {
        if (price != null) {
            priceLargeQuantities.remove(price);
            price.setProduct(null);
        }
    }

    /**
     * Ottiene l'immagine di copertina
     */
    public ProductImageEntity getCoverImage() {
        return productImages.stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsCover()))
                .findFirst()
                .orElse(productImages.isEmpty() ? null : productImages.get(0));
    }

    /**
     * Imposta un'immagine come copertina
     */
    public void setCoverImage(ProductImageEntity newCoverImage) {
        // Rimuovi il flag di copertina da tutte le immagini
        productImages.forEach(img -> img.setIsCover(false));

        // Imposta la nuova immagine di copertina
        if (newCoverImage != null && productImages.contains(newCoverImage)) {
            newCoverImage.setIsCover(true);
        }
    }

    /**
     * Calcola il prezzo finale considerando lo sconto
     */
    public BigDecimal getFinalPrice() {
        if (retailPrice == null)
            return BigDecimal.ZERO;
        if (discount == null || discount <= 0)
            return retailPrice;

        BigDecimal discountAmount = retailPrice.multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100),
                2, RoundingMode.HALF_UP);
        return retailPrice.subtract(discountAmount);
    }

    /**
     * Verifica se il prodotto può essere ordinato
     */
    public boolean canBeOrdered() {
        return isActive() && quantity != null && quantity > 0;
    }

    // Getters and Setters
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

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
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

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getHasAttributes() {
        return hasAttributes;
    }

    public void setHasAttributes(Boolean hasAttributes) {
        this.hasAttributes = hasAttributes;
    }

    public Boolean getHasImages() {
        return hasImages;
    }

    public void setHasImages(Boolean hasImages) {
        this.hasImages = hasImages;
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

    public BigDecimal getInShopsPrice() {
        return inShopsPrice;
    }

    public void setInShopsPrice(BigDecimal inShopsPrice) {
        this.inShopsPrice = inShopsPrice;
    }

    public Boolean getHasTags() {
        return hasTags;
    }

    public void setHasTags(Boolean hasTags) {
        this.hasTags = hasTags;
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public List<ProductAttributesEntity> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttributesEntity> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public List<ProductLargeQuantityPriceEntity> getPriceLargeQuantities() {
        return priceLargeQuantities;
    }

    public void setPriceLargeQuantities(List<ProductLargeQuantityPriceEntity> priceLargeQuantities) {
        this.priceLargeQuantities = priceLargeQuantities;
    }

    public ManufacturerEntity getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerEntity manufacturer) {
        this.manufacturer = manufacturer;
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

    public Set<ProductFeaturesEntity> getProductFeatures() {
        return productFeatures;
    }

    public void setProductFeatures(Set<ProductFeaturesEntity> productFeatures) {
        this.productFeatures = productFeatures;
    }

    public Set<ProductWhyChooseEntity> getWhyChoose() {
        return whyChoose;
    }

    public void setWhyChoose(Set<ProductWhyChooseEntity> whyChoose) {
        this.whyChoose = whyChoose;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ProductEntity that = (ProductEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        return sku != null ? sku.equals(that.sku) : that.sku == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", retailPrice=" + retailPrice +
                ", active=" + active +
                ", reference='" + reference + '\'' +
                '}';
    }
}
