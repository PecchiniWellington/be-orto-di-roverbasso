package com.ortoroverbasso.ortorovebasso.constants.product;

import java.math.RoundingMode;

/**
 * Costanti per il modulo Product
 */
public final class ProductConstants {

    // Cache keys
    public static final String CACHE_PRODUCTS = "products";
    public static final String CACHE_FLAT_PRODUCTS = "flatProducts";

    // Business logic constants
    public static final int LOW_STOCK_THRESHOLD = 10;
    public static final int PRICE_SCALE = 2;
    public static final RoundingMode PRICE_ROUNDING_MODE = RoundingMode.HALF_UP;

    // Validation messages
    public static final String SKU_REQUIRED = "SKU is required";
    public static final String RETAIL_PRICE_REQUIRED = "Retail price is required";
    public static final String ACTIVE_STATUS_REQUIRED = "Active status is required";
    public static final String CATEGORY_REQUIRED = "Category is required";

    // Error messages
    public static final String PRODUCT_NOT_FOUND = "Product not found with ID: ";
    public static final String PRODUCT_NOT_FOUND_SKU = "Product not found with SKU: ";
    public static final String PRODUCT_ALREADY_EXISTS = "Product with SKU '%s' already exists";
    public static final String CATEGORY_NOT_FOUND = "Category not found with ID: ";
    public static final String MANUFACTURER_NOT_FOUND = "Manufacturer not found with ID: ";

    // Success messages
    public static final String PRODUCT_CREATED = "Product created successfully with ID: ";
    public static final String PRODUCT_UPDATED = "Product updated successfully with ID: ";
    public static final String PRODUCT_DELETED = "Product deleted successfully. ID: ";
    public static final String PRODUCT_STATUS_UPDATED = "Product status updated successfully for ID: ";
    public static final String PRODUCT_QUANTITY_UPDATED = "Product quantity updated successfully for ID: ";

    // Database constraints
    public static final int SKU_MAX_LENGTH = 255;
    public static final int REFERENCE_MAX_LENGTH = 255;
    public static final int PRICE_PRECISION = 10;
    public static final int WEIGHT_PRECISION = 8;
    public static final int WEIGHT_SCALE = 3;

    // Default values
    public static final boolean DEFAULT_ACTIVE = true;
    public static final boolean DEFAULT_HAS_ATTRIBUTES = false;
    public static final boolean DEFAULT_HAS_IMAGES = false;
    public static final boolean DEFAULT_HAS_TAGS = false;
    public static final int DEFAULT_QUANTITY = 0;
    public static final int DEFAULT_DISCOUNT = 0;

    // Pagination defaults
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;
    public static final int DEFAULT_PAGE_NUMBER = 0;

    private ProductConstants() {
        // Private constructor to prevent instantiation
    }
}
