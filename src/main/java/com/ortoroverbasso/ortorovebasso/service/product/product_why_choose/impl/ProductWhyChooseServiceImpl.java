package com.ortoroverbasso.ortorovebasso.service.product.product_why_choose.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_why_choose.ProductWhyChooseEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_why_choose.ProductWhyChooseMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_why_choose.ProductWhyChooseRepository;
import com.ortoroverbasso.ortorovebasso.service.product.product_why_choose.IProductWhyChooseService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductWhyChooseServiceImpl implements IProductWhyChooseService {

    @Autowired
    private ProductWhyChooseRepository whyChooseRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductWhyChooseResponseDto create(ProductWhyChooseRequestDto request) {
        ProductWhyChooseEntity entity = new ProductWhyChooseEntity(request.getValue());
        entity = whyChooseRepository.save(entity);
        return ProductWhyChooseMapper.toResponse(entity);
    }

    @Override
    public List<ProductWhyChooseResponseDto> getAll() {

        List<ProductWhyChooseEntity> whyChooseEntities = whyChooseRepository.findAll();

        return whyChooseEntities.stream()
                .map(entity -> {
                    List<Long> productIds = whyChooseRepository.findProductIdsByWhyChooseId(entity.getId());
                    ProductWhyChooseResponseDto response = ProductWhyChooseMapper.toResponse(entity);
                    response.setProductIds(productIds);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductWhyChooseResponseDto getById(Long id) {

        ProductWhyChooseEntity entity = whyChooseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motivo non trovato"));

        List<Long> productIds = whyChooseRepository.findProductIdsByWhyChooseId(entity.getId());

        ProductWhyChooseResponseDto response = ProductWhyChooseMapper.toResponse(entity);

        response.setProductIds(productIds);

        return response;
    }

    @Override
    public ProductWhyChooseResponseDto update(Long id, ProductWhyChooseRequestDto request) {
        ProductWhyChooseEntity entity = whyChooseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motivo non trovato"));
        entity.setValue(request.getValue());
        entity = whyChooseRepository.save(entity);
        return ProductWhyChooseMapper.toResponse(entity);
    }

    @Override
    public void delete(Long id) {
        ProductWhyChooseEntity entity = whyChooseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motivo non trovato"));
        whyChooseRepository.delete(entity);
    }

    @Override
    public ResponseEntity<String> addWhyChooseToProduct(Long productId, List<Long> whyChooseIds) {
        Optional<ProductEntity> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            ProductEntity product = productOptional.get();
            Set<ProductWhyChooseEntity> whyChooseEntities = product.getWhyChoose();

            // Liste per tracciare gli ID aggiunti e quelli già presenti
            List<Long> addedIds = new ArrayList<>();
            List<Long> alreadyAssociatedIds = new ArrayList<>();

            for (Long whyChooseId : whyChooseIds) {
                Optional<ProductWhyChooseEntity> whyChooseEntityOptional = whyChooseRepository.findById(whyChooseId);
                if (whyChooseEntityOptional.isPresent()) {
                    ProductWhyChooseEntity whyChooseEntity = whyChooseEntityOptional.get();

                    // Se l'associazione non è già presente, aggiungila
                    if (!whyChooseEntities.contains(whyChooseEntity)) {
                        whyChooseEntities.add(whyChooseEntity);
                        addedIds.add(whyChooseId);
                    } else {
                        alreadyAssociatedIds.add(whyChooseId);
                    }
                }
            }

            // Aggiorna il prodotto con le nuove associazioni
            product.setWhyChoose(whyChooseEntities);
            productRepository.save(product);

            // Costruisci la risposta con i risultati
            StringBuilder responseMessage = new StringBuilder();

            if (!addedIds.isEmpty()) {
                responseMessage.append("Added associations: ").append(addedIds).append(". ");
            }
            if (!alreadyAssociatedIds.isEmpty()) {
                responseMessage.append("Already associated: ").append(alreadyAssociatedIds).append(". ");
            }

            // Ritorna la risposta con il messaggio
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage.toString());
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + productId);
        }
    }

    @Override
    public ResponseEntity<String> removeWhyChooseFromProduct(Long productId, List<Long> whyChooseIds) {
        Optional<ProductEntity> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            ProductEntity product = productOptional.get();
            Set<ProductWhyChooseEntity> whyChooseEntities = product.getWhyChoose();

            // Liste per tracciare gli ID rimossi e quelli non presenti
            List<Long> removedIds = new ArrayList<>();
            List<Long> notFoundIds = new ArrayList<>();

            for (Long whyChooseId : whyChooseIds) {
                // Cerca se l'associazione esiste
                Optional<ProductWhyChooseEntity> whyChooseEntityOptional = whyChooseRepository.findById(whyChooseId);
                if (whyChooseEntityOptional.isPresent()) {
                    ProductWhyChooseEntity whyChooseEntity = whyChooseEntityOptional.get();

                    // Se l'associazione esiste nel prodotto, rimuovila
                    if (whyChooseEntities.contains(whyChooseEntity)) {
                        whyChooseEntities.remove(whyChooseEntity);
                        removedIds.add(whyChooseId);
                    }
                } else {
                    // Se l'associazione non esiste, aggiungila alla lista notFound
                    notFoundIds.add(whyChooseId);
                }
            }

            // Aggiorna il prodotto con le nuove associazioni
            product.setWhyChoose(whyChooseEntities);
            productRepository.save(product);

            // Costruisci la risposta con i risultati
            StringBuilder responseMessage = new StringBuilder();

            if (!removedIds.isEmpty()) {
                responseMessage.append("Removed associations: ").append(removedIds).append(". ");
            }
            if (!notFoundIds.isEmpty()) {
                responseMessage.append("Associations not found: ").append(notFoundIds).append(". ");
            }

            // Ritorna la risposta con il messaggio
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage.toString());
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + productId);
        }
    }

}
