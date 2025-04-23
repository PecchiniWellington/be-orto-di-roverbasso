package com.ortoroverbasso.ortorovebasso.controller.taxonomy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.taxonomy.TaxonomyRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.taxonomy.TaxonomyResponseDto;
import com.ortoroverbasso.ortorovebasso.service.taxonomy.ITaxonomyService;

@RestController
@RequestMapping("/api/taxonomies")
public class TaxonomyController {

    @Autowired
    private ITaxonomyService taxonomyService;

    @GetMapping("/all")
    public ResponseEntity<List<TaxonomyResponseDto>> getAllTaxonomies() {
        List<TaxonomyResponseDto> taxonomies = taxonomyService.getAllTaxonomies();
        return ResponseEntity.ok(taxonomies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxonomyResponseDto> getTaxonomyById(@PathVariable Long id) {
        TaxonomyResponseDto taxonomy = taxonomyService.getTaxonomyById(id);
        return ResponseEntity.ok(taxonomy);
    }

    @PostMapping
    public ResponseEntity<TaxonomyResponseDto> createTaxonomy(
            @RequestBody TaxonomyRequestDto createTaxonomyRequestDto) {
        TaxonomyResponseDto createdTaxonomy = taxonomyService.createTaxonomy(createTaxonomyRequestDto);
        return ResponseEntity.status(201).body(createdTaxonomy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxonomyResponseDto> updateTaxonomy(
            @PathVariable Long id,
            @RequestBody TaxonomyRequestDto updateTaxonomyRequestDto) {
        TaxonomyResponseDto updatedTaxonomy = taxonomyService.updateTaxonomy(id, updateTaxonomyRequestDto);
        return ResponseEntity.ok(updatedTaxonomy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxonomy(@PathVariable Long id) {
        taxonomyService.deleteTaxonomy(id);
        return ResponseEntity.noContent().build();
    }
}
