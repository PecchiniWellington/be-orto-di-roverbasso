package com.ortoroverbasso.ortorovebasso.service.product.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductInformationEntity;
import com.ortoroverbasso.ortorovebasso.exception.ProductAlreadyExistsException;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductInformationRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductInformationRepository productInformationRepository;

    public ProductServiceImpl(ProductRepository productRepository,
            ProductInformationRepository productInformationRepository) {
        this.productRepository = productRepository;
        this.productInformationRepository = productInformationRepository;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        // Verifica se ProductInformation con lo stesso SKU esiste
        ProductInformationEntity productInformation = productInformationRepository.findBySku(dto.getSku());

        if (productInformation != null) {
            // Verifica se lo SKU è già associato ad un altro prodotto
            ProductEntity existingProduct = productRepository.findBySku(dto.getSku());

            if (existingProduct != null) {
                // Se esiste già un prodotto con lo stesso SKU, ritorna un errore
                throw new ProductAlreadyExistsException(
                        "Product with SKU " + dto.getSku() + " is already associated with another product");
            }
            // Se lo SKU esiste ma non è associato a nessun prodotto, lo associamo
            ProductEntity product = ProductMapper.toEntity(dto);
            product.setProductInformation(productInformation);
            product = productRepository.save(product);
            return ProductMapper.toResponseDto(product);
        }

        // Se il ProductInformation non esiste, creiamo un nuovo ProductInformation
        productInformation = new ProductInformationEntity();
        productInformation.setSku(dto.getSku());
        productInformation.setName(dto.getSku()); // Utilizza il nome dallo SKU o un altro valore
        productInformation.setDescription(dto.getSku()); // Usa una descrizione per il nuovo prodotto
        productInformation.setUrl(dto.getSku());
        productInformation.setIsoCode(dto.getSku());
        productInformation.setDateUpdDescription(dto.getSku());

        // Salva il nuovo ProductInformation
        productInformation = productInformationRepository.save(productInformation);

        // Ora crea il prodotto e associa il ProductInformation
        ProductEntity product = ProductMapper.toEntity(dto);
        product.setProductInformation(productInformation);

        // Salva il nuovo prodotto nel database
        product = productRepository.save(product);

        return ProductMapper.toResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        // Mapping a list of products to response DTOs
        List<ProductEntity> products = productRepository.findAll();
        return ProductMapper.toResponseDto(products);
    }

    @Override
    public ProductResponseDto getProductBySku(String sku) {
        // Get product by SKU and map it to the response DTO
        ProductEntity product = productRepository.findBySku(sku);
        if (product != null) {
            return ProductMapper.toResponseDto(product);
        }
        throw new RuntimeException("Product not found with SKU: " + sku);
    }

    @Override
    public ProductResponseDto getProductInformationBySku(String sku) {
        // Retrieve product information based on SKU
        ProductInformationEntity productInformation = productInformationRepository.findBySku(sku);
        if (productInformation != null) {
            return ProductMapper.toResponseDto(productInformation.getProduct());
        }
        throw new RuntimeException("Product Information not found with SKU: " + sku);
    }
}
