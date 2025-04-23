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

    @Override
    public ManufacturerResponseDto createManufacturer(ManufacturerRequestDto dto) {
        try {
            ManufacturerEntity manufacturer = ManufacturerMapper.toEntity(dto);

            ManufacturerEntity manufacturerSaved = manufacturerRepository.save(manufacturer);

            ManufacturerResponseDto manufacturerResponse = ManufacturerMapper.toResponseDto(manufacturerSaved);

            if (manufacturerResponse.getProducts() == null) {
                manufacturerResponse.setProducts(List.of());
            }

            return manufacturerResponse;

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation while saving manufacturer: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating manufacturer: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ManufacturerResponseDto> getAllManufacturers() {
        try {
            List<ManufacturerEntity> manufacturers = manufacturerRepository.findAll();

            List<ManufacturerResponseDto> manufacturerResponses = manufacturers.stream()
                    .map(ManufacturerMapper::toResponseDto)
                    .toList();

            return manufacturerResponses;

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation while fetching manufacturers: " + e.getMessage(), e);

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating manufacturer: " + e.getMessage(), e);

        }
    }

    @Override
    public ManufacturerResponseDto getManufacturerById(Long id) {
        try {
            ManufacturerEntity manufacturer = manufacturerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Manufacturer not found"));

            ManufacturerResponseDto manufacturerResponse = ManufacturerMapper.toResponseDto(manufacturer);

            return manufacturerResponse;

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation while fetching manufacturer: " + e.getMessage(), e);

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating manufacturer: " + e.getMessage(), e);

        }
    }

    @Override
    public ManufacturerResponseDto updateManufacturer(Long id, ManufacturerRequestDto dto) {
        try {
            ManufacturerEntity manufacturer = manufacturerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Manufacturer not found"));

            ManufacturerEntity updatedManufacturer = ManufacturerMapper.toEntity(dto);
            updatedManufacturer.setId(manufacturer.getId());

            ManufacturerEntity manufacturerSaved = manufacturerRepository.save(updatedManufacturer);

            ManufacturerResponseDto manufacturerResponse = ManufacturerMapper.toResponseDto(manufacturerSaved);

            return manufacturerResponse;

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation while updating manufacturer: " + e.getMessage(), e);

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating manufacturer: " + e.getMessage(), e);

        }
    }

    @Override
    public void deleteManufacturer(Long id) {
        try {
            ManufacturerEntity manufacturer = manufacturerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Manufacturer not found"));

            manufacturerRepository.delete(manufacturer);

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation while deleting manufacturer: " + e.getMessage(), e);

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating manufacturer: " + e.getMessage(), e);

        }
    }

    @Override
    public ManufacturerResponseDto getManufacturerProducts(Long id) {
        try {
            ManufacturerEntity manufacturer = manufacturerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Manufacturer not found"));

            ManufacturerResponseDto manufacturerResponse = ManufacturerMapper.toResponseDto(manufacturer);

            if (manufacturerResponse.getProducts() == null) {
                manufacturerResponse.setProducts(List.of());
            }

            return manufacturerResponse;

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(
                    "Data integrity violation while fetching manufacturer products: " + e.getMessage(), e);

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating manufacturer: " + e.getMessage(), e);

        }
    }

    @Override
    public void associateProductsToManufacturer(Long manufacturerId, List<Long> productIds) {
        ManufacturerEntity manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found"));

        List<ProductEntity> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new EntityNotFoundException("One or more products not found");
        }

        for (ProductEntity product : products) {
            if (product.getManufacturer() != null) {
                throw new ConflictException(
                        "Product with ID " + product.getId() + " is already associated with a manufacturer");
            }
        }

        for (ProductEntity product : products) {
            product.setManufacturer(manufacturer);
        }

        productRepository.saveAll(products);
    }

    @Override
    public void dissociateProductsFromManufacturer(Long manufacturerId, List<Long> productIds) {
        ManufacturerEntity manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found"));

        List<ProductEntity> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new EntityNotFoundException("One or more products not found");
        }

        for (ProductEntity product : products) {
            if (product.getManufacturer() == null || !product.getManufacturer().equals(manufacturer)) {
                throw new ConflictException(
                        "Product with ID " + product.getId() + " is not associated with this manufacturer");
            }
            product.setManufacturer(null);
        }

        productRepository.saveAll(products);
    }

}
