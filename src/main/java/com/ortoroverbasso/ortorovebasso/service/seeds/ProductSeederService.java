package com.ortoroverbasso.ortorovebasso.service.seeds;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.ortoroverbasso.ortorovebasso.entity.product.Product;
import com.ortoroverbasso.ortorovebasso.repository.ProductRepository;

@Service
public class ProductSeederService {

    private final ProductRepository productRepository;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    public ProductSeederService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void seedProducts(int count) {
        for (int i = 0; i < count; i++) {
            Product p = new Product();
            p.setId(faker.number().randomNumber());
            p.setManufacturer(faker.number().randomNumber());
            p.setSku(faker.code().isbn10());
            p.setEan13(faker.code().ean13());
            p.setWeight(random.nextInt(500));
            p.setHeight(random.nextInt(100));
            p.setWidth(random.nextInt(100));
            p.setDepth(random.nextInt(100));
            p.setDateUpd(LocalDateTime.now());
            p.setCategory((long) random.nextInt(10));
            p.setDateUpdDescription(LocalDateTime.now());
            p.setDateUpdImages(LocalDateTime.now());
            p.setDateUpdStock(LocalDateTime.now());
            p.setWholesalePrice(faker.commerce().price());
            p.setRetailPrice(String.format("%.2f", 50 + random.nextDouble() * 150));
            p.setTaxonomy(faker.number().randomNumber());
            p.setDateAdd(LocalDateTime.now());
            p.setVideo("0");
            p.setActive(1);
            p.setAttributes(true);
            p.setCategories(true);
            p.setImages(true);
            p.setTaxRate(21);
            p.setTaxId(21);
            p.setInShopsPrice(100 + random.nextDouble() * 50);
            p.setCondition("New");
            p.setLogisticClass("A");
            p.setTags(true);
            p.setDateUpdProperties(LocalDateTime.now());
            p.setDateUpdCategories(LocalDateTime.now());
            p.setIntrastat(null);
            p.setPartNumber(faker.bothify("??###ABC"));
            p.setCanon(0.0);

            productRepository.save(p);
        }
    }
}
