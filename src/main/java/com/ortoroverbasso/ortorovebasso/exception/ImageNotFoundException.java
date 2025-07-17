package com.ortoroverbasso.ortorovebasso.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException(Long imageId) {
        super("Image not found with ID: " + imageId);
    }

    public ImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
