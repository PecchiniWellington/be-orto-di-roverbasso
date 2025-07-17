package com.ortoroverbasso.ortorovebasso.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.ortoroverbasso.ortorovebasso.dto.product.ProductFlatDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/products")
@Validated
@Tag(name = "Product Management", description = "API per la gestione dei prodotti")
public class ProductController {

        private final IProductService productService;

        @Autowired
        public ProductController(IProductService productService) {
                this.productService = productService;
        }

        @Operation(summary = "Crea un nuovo prodotto")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Prodotto creato con successo"),
                        @ApiResponse(responseCode = "400", description = "Dati di input non validi"),
                        @ApiResponse(responseCode = "409", description = "SKU già esistente")
        })
        @PostMapping
        public ResponseEntity<ProductResponseDto> createProduct(
                        @Valid @RequestBody ProductRequestDto dto) {
                ProductResponseDto createdProduct = productService.createProduct(dto);
                return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        }

        @Operation(summary = "Ottieni tutti i prodotti con paginazione")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista prodotti recuperata con successo")
        })
        @GetMapping("/all")
        public ResponseEntity<PaginatedResponseDto<ProductResponseDto>> getAllProducts(
                        @Parameter(description = "Numero di pagina (0-based)") @RequestParam(defaultValue = "0") @Min(0) int page,
                        @Parameter(description = "Dimensione della pagina") @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {

                Pageable pageable = PageRequest.of(page, size);
                PaginatedResponseDto<ProductResponseDto> products = productService.getAllProducts(pageable);
                return ResponseEntity.ok(products);
        }

        @Operation(summary = "Ottieni un prodotto per ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Prodotto trovato"),
                        @ApiResponse(responseCode = "404", description = "Prodotto non trovato")
        })
        @GetMapping("/{productId}")
        public ResponseEntity<ProductResponseDto> getProductById(
                        @Parameter(description = "ID del prodotto") @PathVariable @Positive Long productId) {
                ProductResponseDto product = productService.getProductById(productId);
                return ResponseEntity.ok(product);
        }

        @Operation(summary = "Aggiorna un prodotto esistente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Prodotto aggiornato con successo"),
                        @ApiResponse(responseCode = "404", description = "Prodotto non trovato"),
                        @ApiResponse(responseCode = "400", description = "Dati di input non validi")
        })
        @PutMapping("/{productId}")
        public ResponseEntity<ProductResponseDto> updateProduct(
                        @Parameter(description = "ID del prodotto") @PathVariable @Positive Long productId,
                        @Valid @RequestBody ProductRequestDto dto) {
                ProductResponseDto updatedProduct = productService.updateProduct(productId, dto);
                return ResponseEntity.ok(updatedProduct);
        }

        @Operation(summary = "Elimina un prodotto")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Prodotto eliminato con successo"),
                        @ApiResponse(responseCode = "404", description = "Prodotto non trovato")
        })
        @DeleteMapping("/{productId}")
        public ResponseEntity<GenericResponseDto> deleteProduct(
                        @Parameter(description = "ID del prodotto") @PathVariable @Positive Long productId) {
                GenericResponseDto response = productService.deleteProduct(productId);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Ottieni prodotti per categoria")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Prodotti della categoria recuperati"),
                        @ApiResponse(responseCode = "404", description = "Categoria non trovata")
        })
        @GetMapping("/category")
        public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(
                        @Parameter(description = "ID della categoria") @RequestParam @Positive Long categoryId) {
                List<ProductResponseDto> products = productService.getProductsByCategory(categoryId);
                return ResponseEntity.ok(products);
        }

        @Operation(summary = "Ottieni prodotti per slug di categoria (incluse sottocategorie)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Prodotti recuperati con successo"),
                        @ApiResponse(responseCode = "404", description = "Categoria non trovata")
        })
        @GetMapping("/category/{slug}")
        public ResponseEntity<List<ProductResponseDto>> getProductsByCategorySlug(
                        @Parameter(description = "Slug della categoria") @PathVariable @NotBlank String slug) {
                List<ProductResponseDto> products = productService.getProductsByCategorySlug(slug);
                return ResponseEntity.ok(products);
        }

        @Operation(summary = "Ottieni prodotti per slug di sottocategoria")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Prodotti della sottocategoria recuperati"),
                        @ApiResponse(responseCode = "404", description = "Sottocategoria non trovata")
        })
        @GetMapping("/subcategory/{slug}")
        public ResponseEntity<List<ProductResponseDto>> getProductsBySubCategorySlug(
                        @Parameter(description = "Slug della sottocategoria") @PathVariable @NotBlank String slug) {
                List<ProductResponseDto> products = productService.getProductsBySubCategorySlug(slug);
                return ResponseEntity.ok(products);
        }

        @Operation(summary = "Filtra prodotti con criteri specifici")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Prodotti filtrati recuperati con successo")
        })
        @PostMapping("/filter")
        public ResponseEntity<PaginatedResponseDto<ProductResponseDto>> getFilteredProducts(
                        @Valid @RequestBody ProductFilterRequestDto filterDto,
                        @Parameter(description = "Parametri di paginazione") Pageable pageable) {
                PaginatedResponseDto<ProductResponseDto> filteredProducts = productService.getFilteredProducts(
                                filterDto,
                                pageable);
                return ResponseEntity.ok(filteredProducts);
        }

        @Operation(summary = "Ottieni filtri disponibili per i prodotti")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Filtri disponibili recuperati con successo")
        })
        @PostMapping("/available-filters")
        public ResponseEntity<ProductFacetResponseDto> getAvailableFilters(
                        @Valid @RequestBody ProductFilterRequestDto filterDto) {
                ProductFacetResponseDto availableFilters = productService.getAvailableFilters(filterDto);
                return ResponseEntity.ok(availableFilters);
        }

        @Operation(summary = "Ottieni lista semplificata di tutti i prodotti (non paginata)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista semplificata prodotti recuperata")
        })
        @GetMapping("/flat-list")
        public ResponseEntity<List<ProductFlatDto>> getFlatProducts() {
                List<ProductFlatDto> products = productService.getFlatProducts();
                return ResponseEntity.ok(products);
        }

        @Operation(summary = "Ottieni lista semplificata di prodotti con paginazione")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista semplificata paginata recuperata")
        })
        @GetMapping("/flat/paginated")
        public ResponseEntity<Page<ProductFlatDto>> getFlatProductsPaginated(
                        @Parameter(description = "Numero di pagina (0-based)") @RequestParam(defaultValue = "0") @Min(0) int page,
                        @Parameter(description = "Dimensione della pagina") @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {

                Pageable pageable = PageRequest.of(page, size);
                Page<ProductFlatDto> products = productService.getFlatProductsPaginated(pageable);
                return ResponseEntity.ok(products);
        }
}
