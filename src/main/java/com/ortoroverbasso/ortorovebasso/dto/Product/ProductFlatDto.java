package com.ortoroverbasso.ortorovebasso.dto.product;

public class ProductFlatDto {
    private Long id;
    private String sku;
    private String reference;
    private String imageUrl;
    private String productName;
    private Double retailPrice;

    public ProductFlatDto(Long id, String sku, String reference, String imageUrl, String productName,
            Double retailPrice) {
        this.id = id;
        this.sku = sku;
        this.reference = reference;
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.retailPrice = retailPrice;
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getReference() {
        return reference;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }
}
