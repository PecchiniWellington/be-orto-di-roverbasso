package com.ortoroverbasso.ortorovebasso.entity.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ProductEntityTest {

    @Test
    public void testProductEntityCreation() {
        // Creazione dell'oggetto ProductEntity
        ProductEntity product = new ProductEntity();

        // Verifica che il prodotto sia stato creato correttamente
        product.setId(1L);
        product.setSku("SKU123");
        product.setWeight(100);
        product.setDateAdd(LocalDateTime.now());

        assertEquals(1L, product.getId());
        assertEquals("SKU123", product.getSku());
        assertEquals(100, product.getWeight());
        assertNotNull(product.getDateAdd());
    }

    @Test
    public void testProductEntityFullConstructor() {
        // Utilizzo del costruttore con tutti i parametri
        ProductEntity product = new ProductEntity(
                1L,
                123L,
                "SKU123",
                "EAN123",
                100,
                10,
                20,
                30,
                LocalDateTime.now(),
                1L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "100.0",
                "150.0",
                1L,
                LocalDateTime.now(),
                "video_url",
                1,
                true,
                true,
                true,
                22,
                1,
                20.0,
                "new",
                "classA",
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "intrastat123",
                "PN123",
                10.0,
                null);

        // Verifica che i valori passati siano correttamente settati
        assertEquals("SKU123", product.getSku());
        assertEquals("EAN123", product.getEan13());
        assertEquals(100, product.getWeight());
    }
}
