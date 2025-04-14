package com.ortoroverbasso.ortorovebasso.dto.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @Schema(description = "SKU del prodotto", example = "ABC123")
    private String sku;

    @Schema(description = "EAN13 del prodotto", example = "1234567890123")
    private String ean13;

    @NotNull(message = "Il peso del prodotto è obbligatorio")
    @Schema(description = "Peso del prodotto in grammi", example = "200")
    private Integer weight;

    @NotNull(message = "La categoria del prodotto è obbligatoria")
    @Schema(description = "ID della categoria del prodotto", example = "1")
    private Long category;

    @Schema(description = "Prezzo al dettaglio del prodotto", example = "299.99")
    private String retailPrice;

    @NotNull(message = "Il prodotto deve essere attivo o meno")
    @Schema(description = "Indica se il prodotto è attivo o meno", example = "true")
    private Boolean active;

}
