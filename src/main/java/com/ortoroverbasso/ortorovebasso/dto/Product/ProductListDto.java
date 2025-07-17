package com.ortoroverbasso.ortorovebasso.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO record per rappresentazione semplificata di prodotti in lista")
public record ProductListDto(
                @Schema(description = "ID del prodotto", example = "1") Long id,

                @Schema(description = "Codice di riferimento", example = "REF-ABC123") String reference,

                @Schema(description = "Prezzo al dettaglio", example = "299.99") Double retailPrice,

                @Schema(description = "URL dell'immagine principale", example = "https://example.com/image.jpg") String imageUrl) {

        // Metodi utility
        public boolean hasImage() {
                return imageUrl != null && !imageUrl.trim().isEmpty();
        }

        public String getFormattedPrice() {
                return retailPrice != null ? String.format("%.2f €", retailPrice) : "N/A";
        }
}
