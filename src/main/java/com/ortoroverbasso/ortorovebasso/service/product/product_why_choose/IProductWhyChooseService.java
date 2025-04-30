package com.ortoroverbasso.ortorovebasso.service.product.product_why_choose;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_why_choose.ProductWhyChooseResponseDto;

public interface IProductWhyChooseService {

    ProductWhyChooseResponseDto create(ProductWhyChooseRequestDto request);

    List<ProductWhyChooseResponseDto> getAll();

    ProductWhyChooseResponseDto getById(Long id);

    ProductWhyChooseResponseDto update(Long id, ProductWhyChooseRequestDto request);

    void delete(Long id);

    // Metodi per l'associazione e dissociazione
    ResponseEntity<String> addWhyChooseToProduct(Long productId, List<Long> whyChooseIds);

    ResponseEntity<String> removeWhyChooseFromProduct(Long productId, List<Long> whyChooseIds);
}
