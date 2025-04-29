package com.ortoroverbasso.ortorovebasso.dto.product.product_features;

import java.util.List;

public class ProductConnectFeaturesRequestDto {

    private Long idConnect;
    private List<Long> featuresId;

    public ProductConnectFeaturesRequestDto(
            Long idConnect,
            List<Long> featuresId) {
        this.idConnect = idConnect;
        this.featuresId = featuresId;

    }

    // Getters and Setters
    public Long getIdConnect() {
        return idConnect;
    }

    public void setIdConnect(Long idConnect) {
        this.idConnect = idConnect;
    }

    public List<Long> getFeaturesId() {
        return featuresId;
    }

    public void setFeaturesId(List<Long> featuresId) {
        this.featuresId = featuresId;

    }

}
