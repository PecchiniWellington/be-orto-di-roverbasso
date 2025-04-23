package com.ortoroverbasso.ortorovebasso.controller.manufacturer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.manufacturer.ManufacturerProductAssociationRequestDto;
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

    @GetMapping("/all")
    public List<ManufacturerResponseDto> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }

    @GetMapping("/{id}")
    public ManufacturerResponseDto getManufacturerById(@PathVariable Long id) {
        return manufacturerService.getManufacturerById(id);
    }

    @PutMapping("/{id}")
    public ManufacturerResponseDto updateManufacturer(@PathVariable Long id, @RequestBody ManufacturerRequestDto dto) {
        return manufacturerService.updateManufacturer(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
    }

    @GetMapping("/{id}/products")
    public ManufacturerResponseDto getManufacturerProducts(@PathVariable Long id) {
        return manufacturerService.getManufacturerProducts(id);
    }

    @PostMapping("/{id}/associate-products")
    public ResponseEntity<String> associateProductsToManufacturer(
            @PathVariable Long id,
            @RequestBody ManufacturerProductAssociationRequestDto request) {
        manufacturerService.associateProductsToManufacturer(id, request.getProductIds());
        return ResponseEntity.ok("Products successfully associated with the manufacturer");
    }

    @PatchMapping("/{id}/disassociate-products")
    public ResponseEntity<String> dissociateProductsFromManufacturer(
            @PathVariable Long id,
            @RequestBody ManufacturerProductAssociationRequestDto request) {
        manufacturerService.dissociateProductsFromManufacturer(id, request.getProductIds());
        return ResponseEntity.ok("Products successfully disassociated from the manufacturer");
    }

}
