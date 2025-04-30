package com.ortoroverbasso.ortorovebasso.service.product.product_features.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductConnectFeaturesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_features.ProductFeaturesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_features.ProductFeaturesEntity;
import com.ortoroverbasso.ortorovebasso.exception.FeatureNotFoundException;
import com.ortoroverbasso.ortorovebasso.exception.ImageNotFoundException;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_features.ProductFeaturesMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_features.ProductFeaturesRepository;
import com.ortoroverbasso.ortorovebasso.service.product.product_features.IProductFeaturesService;

import jakarta.transaction.Transactional;

@Service
public class ProductFeaturesImpl implements IProductFeaturesService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductFeaturesRepository productFeaturesRepository;

    private ProductEntity findProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductFeaturesEntity findFeatureOrThrow(Long id) {
        return productFeaturesRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    @Transactional
    @Override
    public ProductFeaturesResponseDto createProductFeatures(Long productId, ProductFeaturesRequestDto productFeatures) {

        ProductFeaturesEntity productFeaturesEntity = ProductFeaturesMapper.toEntity(productFeatures);

        ProductFeaturesEntity savedFeature = productFeaturesRepository.save(productFeaturesEntity);

        ProductConnectFeaturesRequestDto featureConnectRequest = new ProductConnectFeaturesRequestDto(productId,
                List.of(savedFeature.getId()));

        connectFeatureToProduct(featureConnectRequest);

        return ProductFeaturesMapper.toResponseDto(savedFeature);
    }

    @Transactional
    public List<ProductFeaturesResponseDto> connectFeatureToProduct(
            ProductConnectFeaturesRequestDto featuresRequestDto) {

        ProductEntity product = findProductOrThrow(featuresRequestDto.getIdConnect());

        List<ProductFeaturesResponseDto> connected = new ArrayList<>();

        for (Long featureId : featuresRequestDto.getFeaturesId()) {
            ProductFeaturesEntity featureEntity = findFeatureOrThrow(featureId);

            product.getProductFeatures().add(featureEntity);

        }

        productRepository.save(product);

        for (Long featureId : featuresRequestDto.getFeaturesId()) {
            ProductFeaturesEntity featureEntity = findFeatureOrThrow(featureId);
            ProductFeaturesResponseDto featureDto = ProductFeaturesMapper.toResponseDto(featureEntity);
            connected.add(featureDto);
        }

        return connected;
    }

    @Override
    public ProductFeaturesResponseDto updateProductFeatures(
            Long productId,
            ProductFeaturesRequestDto productFeatures,
            Long featureId) {
        ProductEntity product = findProductOrThrow(productId);

        ProductFeaturesEntity existingFeature = productFeaturesRepository.findById(featureId)
                .orElseThrow(() -> new IllegalArgumentException("Feature not found with ID: " + featureId));

        // Aggiorna le proprietà della feature esistente con i nuovi dati dal DTO
        existingFeature.setFeature(productFeatures.getFeature());
        existingFeature.setValue(productFeatures.getValue());
        // Aggiungi altre proprietà da aggiornare se necessario

        // Salva la feature aggiornata
        ProductFeaturesEntity updatedFeature = productFeaturesRepository.save(existingFeature);

        // Se la feature non è già presente nella lista delle features del prodotto,
        // aggiungila
        if (!product.getProductFeatures().contains(updatedFeature)) {
            product.getProductFeatures().add(updatedFeature);
            productRepository.save(product); // Salva il prodotto dopo aver aggiornato la sua lista di feature
        }

        return ProductFeaturesMapper.toResponseDto(updatedFeature);
    }

    @Override
    public List<ProductFeaturesResponseDto> getAllProductFeatures(Long productId) {
        ProductEntity product = findProductOrThrow(productId);

        List<ProductFeaturesResponseDto> features = new ArrayList<>();

        for (ProductFeaturesEntity feature : product.getProductFeatures()) {
            features.add(ProductFeaturesMapper.toResponseDto(feature));
        }

        return features;
    }

    @Override
    public void deleteProductFeatures(Long productId, Long featureId) {
        // Trova il prodotto
        ProductEntity product = findProductOrThrow(productId);

        // Trova la feature
        ProductFeaturesEntity feature = findFeatureOrThrow(featureId);

        // Rimuovi la feature dalla lista del prodotto
        if (!product.getProductFeatures().remove(feature)) {
            throw new FeatureNotFoundException(featureId);
        }

        // Salva il prodotto per aggiornare la relazione nel database
        productRepository.save(product); // Questo aggiorna il prodotto senza eliminare il prodotto stesso

        // Se necessario, puoi eliminare la feature, ma solo se non è più associata a
        // nessun altro prodotto
        // Se la relazione è ManyToMany, non è necessario eliminare la feature, ma solo
        // rimuoverla dalla lista
        productFeaturesRepository.delete(feature); // Rimuove la feature dal DB
    }

}
