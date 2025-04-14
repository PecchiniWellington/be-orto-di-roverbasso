package com.ortoroverbasso.ortorovebasso.dto.ProductInformation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO per la risposta delle informazioni sul prodotto.
 * Contiene i dettagli relativi a un prodotto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInformationResponseDto {

    @Schema(description = "Identificativo univoco del prodotto.")
    private Long id;

    @Schema(description = "Codice SKU (Stock Keeping Unit) del prodotto.")
    private String sku;

    @Schema(description = "Nome del prodotto.")
    private String name;

    @Schema(description = "Descrizione del prodotto.")
    private String description;

    @Schema(description = "URL associato al prodotto.")
    private String url;

    @Schema(description = "Codice ISO associato al prodotto.")
    private String isoCode;

    @Schema(description = "Data di aggiornamento della descrizione del prodotto.")
    private String dateUpdDescription;
}
