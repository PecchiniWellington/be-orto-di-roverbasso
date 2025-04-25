package com.ortoroverbasso.ortorovebasso.dto.images;

import java.util.List;

public class ImageConnectResponse {

    private String message;
    private List<ImagesDetailDto> images;

    public ImageConnectResponse() {
    }

    public ImageConnectResponse(String message, List<ImagesDetailDto> images) {
        this.message = message;
        this.images = images;
    }

    // Getters e setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ImagesDetailDto> getImages() {
        return images;
    }

    public void setImages(List<ImagesDetailDto> images) {
        this.images = images;
    }
}
