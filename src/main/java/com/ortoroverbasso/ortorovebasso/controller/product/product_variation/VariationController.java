package com.ortoroverbasso.ortorovebasso.controller.product.product_variation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.VariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.VariationResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.product_variation.IVariationService;

@RestController
@RequestMapping("/variations")
public class VariationController {

    @Autowired
    private IVariationService variationService;

    @GetMapping("/{id}")
    public VariationResponseDto getVariation(@PathVariable Long id) {
        return variationService.getVariationById(id);
    }

    @GetMapping
    public List<VariationResponseDto> getAllVariations() {
        return variationService.getAllVariations();
    }

    @PostMapping
    public VariationResponseDto createVariation(@RequestBody VariationRequestDto variationRequestDto) {
        return variationService.createVariation(variationRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteVariation(@PathVariable Long id) {
        variationService.deleteVariation(id);
    }
}
