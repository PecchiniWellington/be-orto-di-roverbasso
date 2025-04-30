package com.ortoroverbasso.ortorovebasso.controller.product.product_why_choose;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseIdsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_why_choose.IProductWhyChooseService;

@RestController
@RequestMapping("/api/products")
public class ProductWhyChooseController {

    @Autowired
    private IProductWhyChooseService service;

    @PostMapping("/whychoose")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductWhyChooseResponseDto create(@RequestBody ProductWhyChooseRequestDto request) {
        return service.create(request);
    }

    @GetMapping("/whychoose")
    public List<ProductWhyChooseResponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/whychoose/{id}")
    public ProductWhyChooseResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/whychoose/{id}")
    public ProductWhyChooseResponseDto update(@PathVariable Long id,
            @RequestBody ProductWhyChooseRequestDto request) {
        return service.update(id, request);
    }

    @DeleteMapping("/whychoose/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{productId}/whychoose/associate")
    public ResponseEntity<Void> addWhyChooseToProduct(
            @PathVariable Long productId,
            @RequestBody ProductWhyChooseIdsRequestDto productWhyChooseIdsDTO) {

        service.addWhyChooseToProduct(productId, productWhyChooseIdsDTO.getWhyChooseIds());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{productId}/whychoose/dissociate")
    public ResponseEntity<Void> removeWhyChooseFromProduct(
            @PathVariable Long productId,
            @RequestBody ProductWhyChooseIdsRequestDto productWhyChooseIdsDTO) {

        service.removeWhyChooseFromProduct(productId, productWhyChooseIdsDTO.getWhyChooseIds());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
