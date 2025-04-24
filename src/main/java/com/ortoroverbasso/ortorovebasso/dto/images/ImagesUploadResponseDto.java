package com.ortoroverbasso.ortorovebasso.dto.images;

public class ImagesUploadResponseDto {
    private String url;
    private String filename;
    private String message;

    public ImagesUploadResponseDto(
            String url,
            String filename,
            String message) {
        this.url = url;
        this.filename = filename;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public String getFilename() {
        return filename;
    }

    public String getMessage() {
        return message;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
