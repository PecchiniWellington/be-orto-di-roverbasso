package com.ortoroverbasso.ortorovebasso.dto.product.product_variation;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;

public class ProductVariationResponseDto {
    private Long id;
    private String sku;
    private String ean13;
    private Double extraWeight;
    private Double wholesalePrice;
    private Double retailPrice;
    private Double inShopsPrice;
    private Double width;
    private Double height;
    private Double depth;
    private List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities;
    private String logisticClass;
    private String partNumber;
    private Integer canon;
    private Long productId;

    // All-args constructor
    public ProductVariationResponseDto(
            Long id,
            String sku,
            String ean13,
            Double extraWeight,
            Double wholesalePrice,
            Double retailPrice,
            Double inShopsPrice,
            Double width,
            Double height,
            Double depth,
            String logisticClass,
            String partNumber,
            Integer canon,
            List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities,
            Long productId) {
        this.id = id;
        this.sku = sku;
        this.ean13 = ean13;
        this.extraWeight = extraWeight;
        this.wholesalePrice = wholesalePrice;
        this.retailPrice = retailPrice;
        this.inShopsPrice = inShopsPrice;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.logisticClass = logisticClass;
        this.partNumber = partNumber;
        this.canon = canon;
        this.priceLargeQuantities = priceLargeQuantities;
        this.productId = productId;
    }

    // No-args constructor
    public ProductVariationResponseDto() {
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

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public Double getExtraWeight() {
        return extraWeight;
    }

    public void setExtraWeight(Double extraWeight) {
        this.extraWeight = extraWeight;
    }

    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Double getInShopsPrice() {
        return inShopsPrice;
    }

    public void setInShopsPrice(Double inShopsPrice) {
        this.inShopsPrice = inShopsPrice;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public String getLogisticClass() {
        return logisticClass;
    }

    public void setLogisticClass(String logisticClass) {
        this.logisticClass = logisticClass;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Integer getCanon() {
        return canon;
    }

    public void setCanon(Integer canon) {
        this.canon = canon;
    }

    public List<ProductLargeQuantityPriceResponseDto> getPriceLargeQuantities() {
        return priceLargeQuantities;
    }

    public void setPriceLargeQuantities(List<ProductLargeQuantityPriceResponseDto> priceLargeQuantities) {
        this.priceLargeQuantities = priceLargeQuantities;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
