package com.ortoroverbasso.ortorovebasso.dto.product;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesShortDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseResponseNoProductIdDto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductResponseDto {

    @Schema(description = "ID del prodotto", example = "1")
    private Long id;

    @Schema(description = "SKU del prodotto", example = "ABC123")
    private String sku;

    @Schema(description = "Prezzo al dettaglio del prodotto", example = "299.99")
    private String retailPrice;

    @Schema(description = "Peso del prodotto in grammi", example = "200")
    private Integer weight;

    @Schema(description = "Indica se il prodotto è attivo o meno", example = "true")
    private Integer active;

    private String wholesalePrice;
    private Double inShopsPrice;

    private Boolean tags;
    private Long manufacturer;
    private Integer discount;

    private List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities;
    private Boolean attributes;
    private List<ProductImagesShortDto> productImages;
    private ProductInformationResponseDto productInformation;
    private List<ProductFeaturesResponseDto> productFeatures;
    private List<ProductWhyChooseResponseNoProductIdDto> whyChoose;

    public ProductResponseDto() {
    }

    // Getter e Setter
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

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(String wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
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

    public Long getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Long manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public List<ProductLargeQuantityPriceResponseDto> getPriceLargeQuantities() {
        return priceLargeQuantities;
    }

    public void setPriceLargeQuantities(List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities) {
        this.priceLargeQuantities = priceLargeQuantities;
    }

    public Boolean getAttributes() {
        return attributes;
    }

    public void setAttributes(Boolean attributes) {
        this.attributes = attributes;
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
}
