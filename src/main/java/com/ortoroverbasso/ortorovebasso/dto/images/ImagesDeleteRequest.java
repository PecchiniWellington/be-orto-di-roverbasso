package com.ortoroverbasso.ortorovebasso.dto.images;

import java.util.List;

public class ImagesDeleteRequest {
    private List<Long> imageIds;

    public ImagesDeleteRequest() {
    }

    public ImagesDeleteRequest(List<Long> imageIds) {
        this.imageIds = imageIds;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Long> imageIds) {
        this.imageIds = imageIds;
    }
}
