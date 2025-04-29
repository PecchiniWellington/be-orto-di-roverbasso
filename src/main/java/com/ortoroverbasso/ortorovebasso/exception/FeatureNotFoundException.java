package com.ortoroverbasso.ortorovebasso.exception;

public class FeatureNotFoundException extends RuntimeException {
    public FeatureNotFoundException(Long id) {
        super("Feature con ID " + id + " non trovata.");
    }
}
