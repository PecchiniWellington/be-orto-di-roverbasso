package com.ortoroverbasso.ortorovebasso.exception;

public class ManufacturerNotFoundException extends RuntimeException {

    public ManufacturerNotFoundException(String message) {
        super(message);
    }

    public ManufacturerNotFoundException(Long manufacturerId) {
        super("Manufacturer not found with ID: " + manufacturerId);
    }

    public ManufacturerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
