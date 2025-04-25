package com.ortoroverbasso.ortorovebasso.dto.images;

import java.util.List;

public class ImageConnectRequestDto {
    private Long idConnect;
    private List<Long> imageIds;

    public ImageConnectRequestDto(
            Long idConnect,
            List<Long> imageIds) {
        this.idConnect = idConnect;
        this.imageIds = imageIds;

    }

    // Getters and Setters
    public Long getIdConnect() {
        return idConnect;
    }

    public void setIdConnect(Long idConnect) {
        this.idConnect = idConnect;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Long> imageIds) {
        this.imageIds = imageIds;
    }

}
