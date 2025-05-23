package com.ortoroverbasso.ortorovebasso.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.paginate.PaginatedResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFacetResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto dto) {
        return productService.createProduct(dto);
    }

    @GetMapping("/all")
    public PaginatedResponseDto<ProductResponseDto> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.getAllProducts(PageRequest.of(page, size));
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    public ProductResponseDto updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequestDto dto) {
        return productService.updateProduct(productId, dto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<GenericResponseDto> deleteProduct(@PathVariable Long productId) {
        GenericResponseDto response = productService.deleteProduct(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/subcategories")
    public List<ProductResponseDto> getProductsByCategory(@RequestParam Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    // Endpoint per ottenere i prodotti da una categoria e tutte le sue
    // sottocategorie
    @GetMapping("/category/{slug}")
    public List<ProductResponseDto> getProductsByCategorySlug(@PathVariable String slug) {
        return productService.getProductsByCategorySlug(slug);
    }

    // Endpoint per ottenere i prodotti di una specifica sottocategoria
    @GetMapping("/subcategory/{slug}")
    public List<ProductResponseDto> getProductsBySubCategorySlug(@PathVariable String slug) {
        return productService.getProductsByCategorySlug(slug);
    }

    @PostMapping("/filter")
    public PaginatedResponseDto<ProductResponseDto> getFilteredProducts(
            @RequestBody ProductFilterRequestDto filterDto,
            Pageable pageable) {
        return productService.getFilteredProducts(filterDto, pageable);
    }

    @PostMapping("/available-filters")
    public ProductFacetResponseDto getAvailableFilters(
            @RequestBody ProductFilterRequestDto filterDto) {
        return productService.getAvailableFilters(filterDto);
    }

}
