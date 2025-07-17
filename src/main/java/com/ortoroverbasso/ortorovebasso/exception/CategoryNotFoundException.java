package com.ortoroverbasso.ortorovebasso.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Long categoryId) {
        super("Category not found with ID: " + categoryId);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
