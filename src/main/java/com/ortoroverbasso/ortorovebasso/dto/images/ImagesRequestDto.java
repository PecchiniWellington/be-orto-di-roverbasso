
package com.ortoroverbasso.ortorovebasso.dto.images;

import java.util.List;

public class ImagesRequestDto {
    private long id;
    private List<ImagesDetailDto> imagesDetail;

    public ImagesRequestDto() {
    }

    public ImagesRequestDto(long id, List<ImagesDetailDto> imagesDetail) {
        this.id = id;
        this.imagesDetail = imagesDetail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ImagesDetailDto> getImages() {
        return imagesDetail;
    }

    public void setImages(List<ImagesDetailDto> images) {
        this.imagesDetail = images;
    }
}
