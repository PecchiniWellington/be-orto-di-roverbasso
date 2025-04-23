package com.ortoroverbasso.ortorovebasso.dto.product.product_information;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO per la risposta delle informazioni sul prodotto.
 * Contiene i dettagli relativi a un prodotto.
 */
public class ProductInformationRequestDto {

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

    // Costruttore senza argomenti
    public ProductInformationRequestDto() {
    }

    // Costruttore con tutti gli argomenti
    public ProductInformationRequestDto(
            Long id,
            String sku,
            String name,
            String description,
            String url,
            String isoCode,
            String dateUpdDescription) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.url = url;
        this.isoCode = isoCode;
        this.dateUpdDescription = dateUpdDescription;
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getDateUpdDescription() {
        return dateUpdDescription;
    }

    public void setDateUpdDescription(String dateUpdDescription) {
        this.dateUpdDescription = dateUpdDescription;
    }
}
