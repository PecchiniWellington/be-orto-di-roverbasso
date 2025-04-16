package com.ortoroverbasso.ortorovebasso.entity.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;

@SpringBootTest
public class ProductEntitySpringTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        ProductEntity product = new ProductEntity();
        product.setSku("SKU123");
        product.setWeight(100);

        productRepository.save(product);

        ProductEntity retrievedProduct = productRepository.findById(product.getId()).orElse(null);
        assertNotNull(retrievedProduct);
        assertEquals("SKU123", retrievedProduct.getSku());
    }
}
