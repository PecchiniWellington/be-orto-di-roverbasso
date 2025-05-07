package com.ortoroverbasso.ortorovebasso.enums;

public enum ProductSortEnum {
    PRICE_ASC,
    PRICE_DESC,
    NAME_ASC,
    NAME_DESC;

    public static ProductSortEnum fromString(String value) {
        if (value == null)
            return null;
        try {
            return ProductSortEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
