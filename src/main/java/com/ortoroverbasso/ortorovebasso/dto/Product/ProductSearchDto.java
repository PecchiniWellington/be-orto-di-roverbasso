package com.ortoroverbasso.ortorovebasso.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO per la ricerca di prodotti")
public class ProductSearchDto {

    @NotBlank(message = "Search term is required")
    @Size(min = 2, max = 100, message = "Search term must be between 2 and 100 characters")
    @Schema(description = "Termine di ricerca", example = "smartphone", required = true)
    private String searchTerm;

    @Schema(description = "ID della categoria per filtrare la ricerca", example = "1")
    private Long categoryId;

    @Schema(description = "Prezzo minimo", example = "100.0")
    private Double minPrice;

    @Schema(description = "Prezzo massimo", example = "500.0")
    private Double maxPrice;

    @Schema(description = "Includi solo prodotti attivi", example = "true")
    private Boolean activeOnly = true;

    // Constructors
    public ProductSearchDto() {
    }

    public ProductSearchDto(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    // Getters and Setters
    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Boolean getActiveOnly() {
        return activeOnly;
    }

    public void setActiveOnly(Boolean activeOnly) {
        this.activeOnly = activeOnly;
    }

    @Override
    public String toString() {
        return "ProductSearchDto{" +
                "searchTerm='" + searchTerm + '\'' +
                ", categoryId=" + categoryId +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", activeOnly=" + activeOnly +
                '}';
    }
}
