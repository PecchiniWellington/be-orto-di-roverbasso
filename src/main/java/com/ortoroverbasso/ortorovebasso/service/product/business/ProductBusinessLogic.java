package com.ortoroverbasso.ortorovebasso.service.product.business;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.ortoroverbasso.ortorovebasso.constants.product.ProductConstants;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;
import com.ortoroverbasso.ortorovebasso.utils.validation.ValidationUtils;

/**
 * Logica di business per i prodotti usando utilities centralizzate
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
     * Valida i dati del prodotto per la creazione usando ValidationUtils
     */
    public void validateForCreate(ProductEntity product) {
        ValidationUtils.validateNotNull(product, "Product");
        ValidationUtils.validateNotBlank(product.getSku(), "SKU");
        ValidationUtils.validateMaxLength(product.getSku(), ProductConstants.SKU_MAX_LENGTH, "SKU");
        ValidationUtils.validateNotNull(product.getRetailPrice(), "Retail Price");
        ValidationUtils.validatePositive(product.getRetailPrice(), "Retail Price");
        ValidationUtils.validateNotNull(product.getCategory(), "Category");

        // Validazioni opzionali
        ValidationUtils.validatePositiveOrZero(product.getWholesalePrice(), "Wholesale Price");
        ValidationUtils.validatePositiveOrZero(product.getInShopsPrice(), "In Shops Price");
        ValidationUtils.validatePositiveOrZero(product.getWeight(), "Weight");
        ValidationUtils.validatePositiveOrZero(product.getQuantity(), "Quantity");
        ValidationUtils.validatePositiveOrZero(product.getDiscount(), "Discount");

        if (product.getReference() != null) {
            ValidationUtils.validateMaxLength(product.getReference(), ProductConstants.REFERENCE_MAX_LENGTH,
                    "Reference");
        }

        // Validazioni business specifiche
        if (product.getDiscount() != null) {
            ValidationUtils.validateInRange(product.getDiscount(), 0, 100, "Discount");
        }
    }

    /**
     * Valida i dati del prodotto per l'aggiornamento usando ValidationUtils
     */
    public void validateForUpdate(ProductEntity product) {
        ValidationUtils.validateNotNull(product, "Product");
        ValidationUtils.validateNotNull(product.getId(), "Product ID");

        // Riusa la validazione per creazione
        validateForCreate(product);
    }

    /**
     * Valida un prezzo per range di quantità
     */
    public void validateLargeQuantityPrice(Integer quantity, BigDecimal price) {
        ValidationUtils.validateNotNull(quantity, "Large Quantity");
        ValidationUtils.validatePositive(quantity, "Large Quantity");
        ValidationUtils.validateNotNull(price, "Large Quantity Price");
        ValidationUtils.validatePositive(price, "Large Quantity Price");
    }

    /**
     * Valida i dati di un'immagine
     */
    public void validateProductImage(String url, Boolean isCover) {
        ValidationUtils.validateNotBlank(url, "Image URL");
        ValidationUtils.validateNotNull(isCover, "Is Cover Flag");
    }

    /**
     * Calcola il margine di profitto
     */
    public BigDecimal calculateProfitMargin(ProductEntity product) {
        if (product == null || product.getRetailPrice() == null ||
                product.getWholesalePrice() == null || product.getWholesalePrice().equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        BigDecimal profit = product.getRetailPrice().subtract(product.getWholesalePrice());
        return profit
                .divide(product.getWholesalePrice(), ProductConstants.PRICE_SCALE, ProductConstants.PRICE_ROUNDING_MODE)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Determina se il prodotto necessita di riordino
     */
    public boolean needsReorder(ProductEntity product, Integer reorderThreshold) {
        if (product == null || product.getQuantity() == null) {
            return false;
        }

        int threshold = reorderThreshold != null ? reorderThreshold : ProductConstants.LOW_STOCK_THRESHOLD;
        return product.getQuantity() <= threshold;
    }

    /**
     * Calcola il prezzo per quantità specifica (se presente nelle large quantities)
     */
    public BigDecimal calculatePriceForQuantity(ProductEntity product, Integer requestedQuantity) {
        if (product == null || requestedQuantity == null || requestedQuantity <= 0) {
            return BigDecimal.ZERO;
        }

        // Se non ci sono prezzi per grandi quantità, usa il prezzo al dettaglio
        if (product.getPriceLargeQuantities() == null || product.getPriceLargeQuantities().isEmpty()) {
            return product.getRetailPrice();
        }

        // Trova il prezzo più appropriato per la quantità richiesta
        return product.getPriceLargeQuantities().stream()
                .filter(priceEntity -> requestedQuantity >= priceEntity.getQuantity())
                .max((p1, p2) -> p1.getQuantity().compareTo(p2.getQuantity()))
                .map(priceEntity -> priceEntity.getPrice())
                .orElse(product.getRetailPrice());
    }
}
