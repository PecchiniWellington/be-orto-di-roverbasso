package com.ortoroverbasso.ortorovebasso.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    /*
     * public ProductAlreadyExistsException(String message) {
     * super(message);
     * }
     */

    public ProductAlreadyExistsException(String sku) {
        super("Product with SKU '" + sku + "' already exists");
    }

    public ProductAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
