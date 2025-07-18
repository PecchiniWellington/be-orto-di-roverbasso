package com.ortoroverbasso.ortorovebasso.service.manufacturer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.manufacturer.ManufacturerEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.exception.ConflictException;
import com.ortoroverbasso.ortorovebasso.mapper.manufacturer.ManufacturerMapper;
import com.ortoroverbasso.ortorovebasso.repository.manufacturer.ManufacturerRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.service.manufacturer.IManufacturerService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ManufacturerServiceImpl implements IManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManufacturerMapper manufacturerMapper;

    @Override
    public ManufacturerResponseDto createManufacturer(ManufacturerRequestDto dto) {
        try {
            ManufacturerEntity manufacturer = manufacturerMapper.toEntity(dto);
            ManufacturerEntity saved = manufacturerRepository.save(manufacturer);
            ManufacturerResponseDto response = manufacturerMapper.toResponseDto(saved);

            if (response.getProducts() == null) {
                response.setProducts(List.of());
            }

            return response;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Errore di integrità nei dati durante la creazione del produttore.", e);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la creazione del produttore.", e);
        }
    }

    @Override
    public List<ManufacturerResponseDto> getAllManufacturers() {
        try {
            List<ManufacturerEntity> manufacturers = manufacturerRepository.findAll();
            return manufacturers.stream()
                    .map(manufacturerMapper::toResponseDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il recupero dei produttori.", e);
        }
    }

    @Override
    public ManufacturerResponseDto getManufacturerById(Long id) {
        try {
            ManufacturerEntity manufacturer = manufacturerRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Produttore non trovato"));
            return manufacturerMapper.toResponseDto(manufacturer);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il recupero del produttore.", e);
        }
    }

    @Override
    public ManufacturerResponseDto updateManufacturer(Long id, ManufacturerRequestDto dto) {
        try {
            ManufacturerEntity existing = manufacturerRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Produttore non trovato"));

            manufacturerMapper.updateEntityFromDto(dto, existing);
            ManufacturerEntity saved = manufacturerRepository.save(existing);

            return manufacturerMapper.toResponseDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Errore di integrità nei dati durante l'aggiornamento del produttore.", e);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'aggiornamento del produttore.", e);
        }
    }

    @Override
    public void deleteManufacturer(Long id) {
        try {
            ManufacturerEntity manufacturer = manufacturerRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Produttore non trovato"));
            manufacturerRepository.delete(manufacturer);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'eliminazione del produttore.", e);
        }
    }

    @Override
    public ManufacturerResponseDto getManufacturerProducts(Long id) {
        try {
            ManufacturerEntity manufacturer = manufacturerRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Produttore non trovato"));

            ManufacturerResponseDto response = manufacturerMapper.toResponseDto(manufacturer);

            if (response.getProducts() == null) {
                response.setProducts(List.of());
            }

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il recupero dei prodotti del produttore.", e);
        }
    }

    @Override
    public void associateProductsToManufacturer(Long manufacturerId, List<Long> productIds) {
        ManufacturerEntity manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new EntityNotFoundException("Produttore non trovato"));

        List<ProductEntity> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new EntityNotFoundException("Uno o più prodotti non trovati");
        }

        for (ProductEntity product : products) {
            if (product.getManufacturer() != null) {
                throw new ConflictException("Il prodotto con ID " + product.getId()
                        + " è già associato a un produttore");
            }
            product.setManufacturer(manufacturer);
        }

        productRepository.saveAll(products);
    }

    @Override
    public void dissociateProductsFromManufacturer(Long manufacturerId, List<Long> productIds) {
        ManufacturerEntity manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new EntityNotFoundException("Produttore non trovato"));

        List<ProductEntity> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new EntityNotFoundException("Uno o più prodotti non trovati");
        }

        for (ProductEntity product : products) {
            if (!manufacturer.equals(product.getManufacturer())) {
                throw new ConflictException("Il prodotto con ID " + product.getId()
                        + " non è associato a questo produttore");
            }
            product.setManufacturer(null);
        }

        productRepository.saveAll(products);
    }
}
