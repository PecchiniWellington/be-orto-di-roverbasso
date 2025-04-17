package com.ortoroverbasso.ortorovebasso.service.product.product_variation;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.VariationRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_variation.VariationResponseDto;

public interface IVariationService {
    /**
     * Crea una nuova variazione di prodotto.
     *
     * @param variationRequestDto i dati della variazione da creare
     * @return la variazione di prodotto creata
     */
    VariationResponseDto createVariation(VariationRequestDto variationRequestDto);

    /**
     * Recupera una variazione di prodotto per ID.
     *
     * @param id l'ID della variazione da recuperare
     * @return la variazione di prodotto trovata
     */
    VariationResponseDto getVariationById(Long id);

    /**
     * Recupera tutte le variazioni di prodotto.
     *
     * @return la lista di tutte le variazioni di prodotto
     */
    List<VariationResponseDto> getAllVariations();

    void deleteVariation(Long id);
}
