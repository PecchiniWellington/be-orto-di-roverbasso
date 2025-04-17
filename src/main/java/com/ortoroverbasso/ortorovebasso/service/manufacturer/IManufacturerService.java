package com.ortoroverbasso.ortorovebasso.service.manufacturer;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerResponseDto;

public interface IManufacturerService {

    ManufacturerResponseDto createManufacturer(ManufacturerRequestDto dto);

    List<ManufacturerResponseDto> getAllManufacturers();

    ManufacturerResponseDto getManufacturerById(Long id);

    ManufacturerResponseDto updateManufacturer(Long id, ManufacturerRequestDto dto);

    void deleteManufacturer(Long id);

}
