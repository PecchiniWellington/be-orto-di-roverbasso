package com.ortoroverbasso.ortorovebasso.controller.manufacturer;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerResponseDto;
import com.ortoroverbasso.ortorovebasso.service.manufacturer.IManufacturerService;

@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerController {

    private final IManufacturerService manufacturerService;

    public ManufacturerController(IManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;

    }

    @PostMapping
    public ManufacturerResponseDto createManufacturer(@RequestBody ManufacturerRequestDto dto) {
        return manufacturerService.createManufacturer(dto);
    }

    @GetMapping
    public List<ManufacturerResponseDto> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }
}
