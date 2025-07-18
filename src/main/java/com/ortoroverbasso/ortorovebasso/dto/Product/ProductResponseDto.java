package com.ortoroverbasso.ortorovebasso.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesShortDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseResponseNoProductIdDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO per la risposta contenente i dettagli del prodotto")
public class ProductResponseDto {

    @Schema(description = "ID del prodotto", example = "1")
    private Long id;

    @Schema(description = "SKU del prodotto", example = "ABC123")
    private String sku;

    @Schema(description = "Prezzo al dettaglio del prodotto", example = "299.99")
    private BigDecimal retailPrice;

    @Schema(description = "Peso del prodotto in grammi", example = "200.0")
    private BigDecimal weight;

    @Schema(description = "Indica se il prodotto è attivo o meno", example = "true")
    private Boolean active;

    @Schema(description = "Prezzo all'ingrosso del prodotto", example = "199.99")
    private BigDecimal wholesalePrice;

    @Schema(description = "Prezzo del prodotto nei negozi", example = "249.99")
    private BigDecimal inShopsPrice;

    @Schema(description = "Indica se il prodotto ha tag associati", example = "true")
    private Boolean hasTags;

    @Schema(description = "ID del produttore", example = "5")
    private Long manufacturerId;

    @Schema(description = "Percentuale di sconto applicata", example = "10")
    private Integer discount;

    @Schema(description = "Nome del prodotto", example = "Prodotto di esempio")
    private String productName;

    @Schema(description = "ID della categoria", example = "1")
    private Long categoryId;

    @Schema(description = "Codice di riferimento del prodotto", example = "REF-ABC123")
    private String reference;

    @Schema(description = "Quantità disponibile", example = "100")
    private Integer quantity;

    @Schema(description = "Data di creazione del prodotto")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateAdd;

    @Schema(description = "Indica se il prodotto ha attributi", example = "true")
    private Boolean hasAttributes;

    @Schema(description = "Indica se il prodotto ha immagini", example = "true")
    private Boolean hasImages;

    @Schema(description = "Lista dei prezzi per grandi quantità")
    private List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities;

    @Schema(description = "Lista delle immagini del prodotto")
    private List<ProductImagesShortDto> productImages;

    @Schema(description = "Informazioni dettagliate del prodotto")
    private ProductInformationResponseDto productInformation;

    @Schema(description = "Lista delle caratteristiche del prodotto")
    private List<ProductFeaturesResponseDto> productFeatures;

    @Schema(description = "Lista dei motivi per scegliere questo prodotto")
    private List<ProductWhyChooseResponseNoProductIdDto> whyChoose;

    // Constructors
    public ProductResponseDto() {
    }

    // Builder pattern per facilità di costruzione
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ProductResponseDto dto = new ProductResponseDto();

        public Builder id(Long id) {
            dto.id = id;
            return this;
        }

        public Builder sku(String sku) {
            dto.sku = sku;
            return this;
        }

        public Builder retailPrice(BigDecimal retailPrice) {
            dto.retailPrice = retailPrice;
            return this;
        }

        public Builder weight(BigDecimal weight) {
            dto.weight = weight;
            return this;
        }

        public Builder active(Boolean active) {
            dto.active = active;
            return this;
        }

        public Builder wholesalePrice(BigDecimal wholesalePrice) {
            dto.wholesalePrice = wholesalePrice;
            return this;
        }

        public Builder inShopsPrice(BigDecimal inShopsPrice) {
            dto.inShopsPrice = inShopsPrice;
            return this;
        }

        public Builder hasTags(Boolean hasTags) {
            dto.hasTags = hasTags;
            return this;
        }

        public Builder manufacturerId(Long manufacturerId) {
            dto.manufacturerId = manufacturerId;
            return this;
        }

        public Builder discount(Integer discount) {
            dto.discount = discount;
            return this;
        }

        public Builder productName(String productName) {
            dto.productName = productName;
            return this;
        }

        public Builder categoryId(Long categoryId) {
            dto.categoryId = categoryId;
            return this;
        }

        public Builder reference(String reference) {
            dto.reference = reference;
            return this;
        }

        public Builder quantity(Integer quantity) {
            dto.quantity = quantity;
            return this;
        }

        public Builder dateAdd(LocalDateTime dateAdd) {
            dto.dateAdd = dateAdd;
            return this;
        }

        public Builder hasAttributes(Boolean hasAttributes) {
            dto.hasAttributes = hasAttributes;
            return this;
        }

        public Builder hasImages(Boolean hasImages) {
            dto.hasImages = hasImages;
            return this;
        }

        public Builder priceLargeQuantities(List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities) {
            dto.priceLargeQuantities = priceLargeQuantities;
            return this;
        }

        public Builder productImages(List<ProductImagesShortDto> productImages) {
            dto.productImages = productImages;
            return this;
        }

        public Builder productInformation(ProductInformationResponseDto productInformation) {
            dto.productInformation = productInformation;
            return this;
        }

        public Builder productFeatures(List<ProductFeaturesResponseDto> productFeatures) {
            dto.productFeatures = productFeatures;
            return this;
        }

        public Builder whyChoose(List<ProductWhyChooseResponseNoProductIdDto> whyChoose) {
            dto.whyChoose = whyChoose;
            return this;
        }

        public ProductResponseDto build() {
            return dto;
        }
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

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
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

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public LocalDateTime getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(LocalDateTime dateAdd) {
        this.dateAdd = dateAdd;
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

    public List<ProductLargeQuantityPriceResponseDto> getPriceLargeQuantities() {
        return priceLargeQuantities;
    }

    public void setPriceLargeQuantities(List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities) {
        this.priceLargeQuantities = priceLargeQuantities;
    }

    public List<ProductImagesShortDto> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImagesShortDto> productImages) {
        this.productImages = productImages;
    }

    public ProductInformationResponseDto getProductInformation() {
        return productInformation;
    }

    public void setProductInformation(ProductInformationResponseDto productInformation) {
        this.productInformation = productInformation;
    }

    public List<ProductFeaturesResponseDto> getProductFeatures() {
        return productFeatures;
    }

    public void setProductFeatures(List<ProductFeaturesResponseDto> productFeatures) {
        this.productFeatures = productFeatures;
    }

    public List<ProductWhyChooseResponseNoProductIdDto> getWhyChoose() {
        return whyChoose;
    }

    public void setWhyChoose(List<ProductWhyChooseResponseNoProductIdDto> whyChoose) {
        this.whyChoose = whyChoose;
    }

    @Override
    public String toString() {
        return "ProductResponseDto{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", retailPrice=" + retailPrice +
                ", active=" + active +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
