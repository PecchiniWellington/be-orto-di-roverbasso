package com.ortoroverbasso.ortorovebasso.mapper.product.product_images;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesShortDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;

public class ProductImagesMapper {

    /**
     * Converte un ImagesDetailDto in ProductImageEntity
     */
    public static ProductImageEntity toEntity(
            ImagesDetailDto dto,
            String url,
            ProductEntity product) {

        if (dto == null || url == null) {
            return null;
        }

        ProductImageEntity image = new ProductImageEntity();
        image.setUrl(url);
        image.setProduct(product);
        image.setIsCover(dto.isCover());
        image.setAltText(dto.getName()); // Usa il nome come alt text
        image.setDisplayOrder(dto.getPosition() != null ? dto.getPosition() : 0);

        return image;
    }

    /**
     * Converte direttamente con parametri semplici
     */
    public static ProductImageEntity toEntity(
            String url,
            Boolean isCover,
            String altText,
            Integer displayOrder,
            ProductEntity product) {

        if (url == null || url.trim().isEmpty()) {
            return null;
        }

        ProductImageEntity image = new ProductImageEntity();
        image.setUrl(url.trim());
        image.setIsCover(isCover != null ? isCover : false);
        image.setAltText(altText);
        image.setDisplayOrder(displayOrder != null ? displayOrder : 0);
        image.setProduct(product);

        return image;
    }

    /**
     * Converte ProductImageEntity in ProductImagesResponseDto completo
     */
    public static ProductImagesResponseDto toResponseDto(ProductImageEntity entity) {
        if (entity == null) {
            return null;
        }

        ProductImagesResponseDto response = new ProductImagesResponseDto();
        response.setId(entity.getId());
        response.setUrl(entity.getUrl());
        response.setAltText(entity.getAltText());
        response.setCover(entity.getIsCover());
        response.setDisplayOrder(entity.getDisplayOrder());

        // Se hai bisogno del productId, lo ottieni dalla relazione
        if (entity.getProduct() != null) {
            response.setProductId(entity.getProduct().getId());
        }

        return response;
    }

    /**
     * Converte ProductImageEntity in ProductImagesShortDto (versione semplificata)
     */
    public static ProductImagesShortDto toShortDto(ProductImageEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ProductImagesShortDto(
                entity.getId(),
                entity.getUrl(),
                entity.getIsCover());
    }

    /**
     * Converte una lista di ProductImageEntity in lista di ProductImagesResponseDto
     */
    public static List<ProductImagesResponseDto> toResponseDtoList(List<ProductImageEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(ProductImagesMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Converte una lista di ProductImageEntity in lista di ProductImagesShortDto
     */
    public static List<ProductImagesShortDto> toShortDtoList(List<ProductImageEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(ProductImagesMapper::toShortDto)
                .collect(Collectors.toList());
    }

    /**
     * Aggiorna un ProductImageEntity esistente con i dati dal DTO
     */
    public static void updateEntityFromDto(ProductImageEntity entity, ImagesDetailDto dto) {
        if (entity == null || dto == null) {
            return;
        }

        // Usa il getter che restituisce Boolean (non boolean primitivo)
        if (dto.getIsCover() != null) {
            entity.setIsCover(dto.getIsCover());
        }

        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            entity.setAltText(dto.getName().trim());
        }

        if (dto.getPosition() != null) {
            entity.setDisplayOrder(dto.getPosition());
        }

        // Aggiorna anche l'URL se presente
        if (dto.getUrl() != null && !dto.getUrl().trim().isEmpty()) {
            entity.setUrl(dto.getUrl().trim());
        }

        // Aggiorna altText se presente e diverso dal nome
        if (dto.getAltText() != null && !dto.getAltText().trim().isEmpty()) {
            entity.setAltText(dto.getAltText().trim());
        }
    }

    /**
     * Trova l'immagine di copertina da una lista
     */
    public static ProductImageEntity findCoverImage(List<ProductImageEntity> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }

        return images.stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsCover()))
                .findFirst()
                .orElse(images.get(0)); // Fallback alla prima immagine
    }

    /**
     * Ottiene solo gli URL delle immagini
     */
    public static List<String> extractImageUrls(List<ProductImageEntity> images) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }

        return images.stream()
                .map(ProductImageEntity::getUrl)
                .filter(url -> url != null && !url.trim().isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Crea un ProductImageEntity base con parametri minimi
     */
    public static ProductImageEntity createBasicImage(String url, Boolean isCover) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }

        ProductImageEntity image = new ProductImageEntity();
        image.setUrl(url.trim());
        image.setIsCover(isCover != null ? isCover : false);
        image.setDisplayOrder(0);

        return image;
    }

    /**
     * Verifica se un'entità ha tutti i campi obbligatori
     */
    public static boolean isValidEntity(ProductImageEntity entity) {
        return entity != null &&
                entity.getUrl() != null &&
                !entity.getUrl().trim().isEmpty() &&
                entity.getProduct() != null;
    }

    /**
     * Ordina le immagini per display order e poi per ID
     */
    public static List<ProductImageEntity> sortImages(List<ProductImageEntity> images) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }

        return images.stream()
                .sorted((img1, img2) -> {
                    // Prima ordina per displayOrder
                    int orderCompare = Integer.compare(
                            img1.getDisplayOrder() != null ? img1.getDisplayOrder() : 0,
                            img2.getDisplayOrder() != null ? img2.getDisplayOrder() : 0);

                    // Se displayOrder è uguale, ordina per ID
                    if (orderCompare == 0 && img1.getId() != null && img2.getId() != null) {
                        return Long.compare(img1.getId(), img2.getId());
                    }

                    return orderCompare;
                })
                .collect(Collectors.toList());
    }
}
