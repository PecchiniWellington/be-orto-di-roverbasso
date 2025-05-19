package com.ortoroverbasso.ortorovebasso.enums;

public enum StatusOrderEnum {
    PENDING,
    CONFIRMED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    public static StatusOrderEnum fromString(String value) {
        if (value == null)
            return null;
        try {
            return StatusOrderEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
