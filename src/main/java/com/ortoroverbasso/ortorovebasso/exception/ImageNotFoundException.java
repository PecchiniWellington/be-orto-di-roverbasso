package com.ortoroverbasso.ortorovebasso.exception;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(Long id) {
        super("Immagine con ID " + id + " non trovata.");
    }
}
