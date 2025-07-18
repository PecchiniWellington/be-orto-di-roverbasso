package com.ortoroverbasso.ortorovebasso.utils.validation;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.util.StringUtils;

/**
 * Utility per validazioni riutilizzabili
 */
public final class ValidationUtils {

    private ValidationUtils() {
        // Utility class
    }

    /**
     * Valida che una stringa non sia null o vuota
     */
    public static void validateNotBlank(String value, String fieldName) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    /**
     * Valida che un valore non sia null
     */
    public static void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }

    /**
     * Valida che un numero sia positivo
     */
    public static void validatePositive(BigDecimal value, String fieldName) {
        if (value != null && value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }

    /**
     * Valida che un numero sia positivo o zero
     */
    public static void validatePositiveOrZero(BigDecimal value, String fieldName) {
        if (value != null && value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(fieldName + " must be positive or zero");
        }
    }

    /**
     * Valida che un numero intero sia positivo
     */
    public static void validatePositive(Integer value, String fieldName) {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }

    /**
     * Valida che un numero intero sia positivo o zero
     */
    public static void validatePositiveOrZero(Integer value, String fieldName) {
        if (value != null && value < 0) {
            throw new IllegalArgumentException(fieldName + " must be positive or zero");
        }
    }

    /**
     * Valida che una collezione non sia null o vuota
     */
    public static void validateNotEmpty(Collection<?> collection, String fieldName) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    /**
     * Valida range di valori
     */
    public static void validateRange(BigDecimal value, BigDecimal min, BigDecimal max, String fieldName) {
        if (value != null) {
            if (min != null && value.compareTo(min) < 0) {
                throw new IllegalArgumentException(fieldName + " must be greater than or equal to " + min);
            }
            if (max != null && value.compareTo(max) > 0) {
                throw new IllegalArgumentException(fieldName + " must be less than or equal to " + max);
            }
        }
    }

    /**
     * Valida lunghezza massima stringa
     */
    public static void validateMaxLength(String value, int maxLength, String fieldName) {
        if (value != null && value.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " cannot exceed " + maxLength + " characters");
        }
    }

    /**
     * Valida formato email (basic)
     */
    public static void validateEmail(String email, String fieldName) {
        if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException(fieldName + " must be a valid email address");
        }
    }

    /**
     * Valida che un valore sia in un range
     */
    public static void validateInRange(Integer value, Integer min, Integer max, String fieldName) {
        if (value != null) {
            if (min != null && value < min) {
                throw new IllegalArgumentException(fieldName + " must be greater than or equal to " + min);
            }
            if (max != null && value > max) {
                throw new IllegalArgumentException(fieldName + " must be less than or equal to " + max);
            }
        }
    }
}
