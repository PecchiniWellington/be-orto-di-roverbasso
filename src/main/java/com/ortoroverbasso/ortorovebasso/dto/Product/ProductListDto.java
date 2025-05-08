package com.ortoroverbasso.ortorovebasso.dto.product;

public record ProductListDto(
        Long id,
        String reference,
        Double retailPrice,
        String imageUrl // solo se vuoi la prima immagine
) {
}
