package com.ortoroverbasso.ortorovebasso.service.product.business;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.constants.product.ProductConstants;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;

/**
 * Logica di business per i prodotti
 */
@Component
public class ProductBusinessLogic {

    /**
     * Verifica se il prodotto può essere ordinato
     */
    public boolean canBeOrdered(ProductEntity product) {
        if (product == null) {
            return false;
        }
        return Boolean.TRUE.equals(product.getActive()) &&
                product.getQuantity() != null &&
                product.getQuantity() > 0;
    }

    /**
     * Calcola il prezzo finale considerando lo sconto
     */
    public BigDecimal calculateFinalPrice(ProductEntity product) {
        if (product == null || product.getRetailPrice() == null) {
            return BigDecimal.ZERO;
        }

        if (product.getDiscount() == null || product.getDiscount() <= 0) {
            return product.getRetailPrice();
        }

        BigDecimal discountAmount = product.getRetailPrice()
                .multiply(BigDecimal.valueOf(product.getDiscount()))
                .divide(BigDecimal.valueOf(100), ProductConstants.PRICE_SCALE, ProductConstants.PRICE_ROUNDING_MODE);

        return product.getRetailPrice().subtract(discountAmount);
    }

    /**
     * Verifica se il prodotto ha scorte basse
     */
    public boolean isLowStock(ProductEntity product) {
        if (product == null || product.getQuantity() == null) {
            return false;
        }
        return product.getQuantity() <= ProductConstants.LOW_STOCK_THRESHOLD;
    }

    /**
     * Verifica se il prodotto è in offerta
     */
    public boolean isOnSale(ProductEntity product) {
        if (product == null) {
            return false;
        }
        return product.getDiscount() != null && product.getDiscount() > 0;
    }

    /**
     * Verifica se il prodotto è attivo
     */
    public boolean isActive(ProductEntity product) {
        if (product == null) {
            return false;
        }
        return Boolean.TRUE.equals(product.getActive());
    }

    /**
     * Verifica se il prodotto ha immagini
     */
    public boolean hasImages(ProductEntity product) {
        if (product == null) {
            return false;
        }
        return product.getProductImages() != null && !product.getProductImages().isEmpty();
    }

    /**
     * Verifica se il prodotto ha attributi
     */
    public boolean hasAttributes(ProductEntity product) {
        if (product == null) {
            return false;
        }
        return product.getProductAttributes() != null && !product.getProductAttributes().isEmpty();
    }

    /**
     * Verifica se il prodotto ha tag
     */
    public boolean hasTags(ProductEntity product) {
        if (product == null) {
            return false;
        }
        return Boolean.TRUE.equals(product.getHasTags());
    }

    /**
     * Ottiene l'immagine di copertina
     */
    public ProductImageEntity getCoverImage(ProductEntity product) {
        if (product == null || product.getProductImages() == null || product.getProductImages().isEmpty()) {
            return null;
        }

        return product.getProductImages().stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsCover()))
                .findFirst()
                .orElse(product.getProductImages().get(0)); // Fallback alla prima immagine
    }

    /**
     * Calcola l'importo dello sconto
     */
    public BigDecimal calculateDiscountAmount(ProductEntity product) {
        if (product == null || product.getRetailPrice() == null ||
                product.getDiscount() == null || product.getDiscount() <= 0) {
            return BigDecimal.ZERO;
        }

        return product.getRetailPrice()
                .multiply(BigDecimal.valueOf(product.getDiscount()))
                .divide(BigDecimal.valueOf(100), ProductConstants.PRICE_SCALE, ProductConstants.PRICE_ROUNDING_MODE);
    }

    /**
     * Calcola la percentuale di sconto sul prezzo all'ingrosso
     */
    public BigDecimal calculateWholesaleDiscountPercentage(ProductEntity product) {
        if (product == null || product.getRetailPrice() == null ||
                product.getWholesalePrice() == null || product.getWholesalePrice().equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        BigDecimal difference = product.getRetailPrice().subtract(product.getWholesalePrice());
        return difference
                .divide(product.getRetailPrice(), ProductConstants.PRICE_SCALE, ProductConstants.PRICE_ROUNDING_MODE)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Valida i dati del prodotto per la creazione
     */
    public void validateForCreate(ProductEntity product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.getSku() == null || product.getSku().trim().isEmpty()) {
            throw new IllegalArgumentException("SKU is required");
        }

        if (product.getRetailPrice() == null || product.getRetailPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Retail price must be positive");
        }

        if (product.getCategory() == null) {
            throw new IllegalArgumentException("Category is required");
        }
    }

    /**
     * Valida i dati del prodotto per l'aggiornamento
     */
    public void validateForUpdate(ProductEntity product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.getId() == null) {
            throw new IllegalArgumentException("Product ID is required for update");
        }

        validateForCreate(product);
    }
}
