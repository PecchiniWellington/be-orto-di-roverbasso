package com.ortoroverbasso.ortorovebasso.service.manufacturer;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerResponseDto;

public interface IManufacturerService {

    ManufacturerResponseDto createManufacturer(ManufacturerRequestDto dto);

    List<ManufacturerResponseDto> getAllManufacturers();

    ManufacturerResponseDto getManufacturerById(Long id);

    ManufacturerResponseDto updateManufacturer(Long id, ManufacturerRequestDto dto);

    ManufacturerResponseDto getManufacturerProducts(Long id);

    void deleteManufacturer(Long id);

    void associateProductsToManufacturer(Long manufacturerId, List<Long> productIds);

    void dissociateProductsFromManufacturer(Long manufacturerId, List<Long> productIds);

}
