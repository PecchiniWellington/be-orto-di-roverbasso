package com.ortoroverbasso.ortorovebasso.service.product.product_images;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;

/**
 * Interfaccia del servizio per la gestione delle immagini dei prodotti
 */
public interface IProductImagesService {

    /**
     * Carica un'immagine per un prodotto specifico
     *
     * @param productId ID del prodotto
     * @param file      File immagine da caricare
     * @return Lista delle immagini del prodotto dopo l'upload
     * @throws ProductNotFoundException se il prodotto non esiste
     * @throws RuntimeException         se l'upload fallisce
     */
    List<ProductImagesResponseDto> uploadProductImage(Long productId, MultipartFile file);

    /**
     * Recupera tutte le immagini di un prodotto
     *
     * @param productId ID del prodotto
     * @return Lista delle immagini del prodotto
     * @throws ProductNotFoundException se il prodotto non esiste
     */
    List<ProductImagesResponseDto> getImagesByProductId(Long productId);

    /**
     * Elimina specifiche immagini di un prodotto
     *
     * @param productId ID del prodotto
     * @param imageIds  Lista degli ID delle immagini da eliminare
     * @return Lista delle immagini eliminate
     * @throws ProductNotFoundException se il prodotto non esiste
     */
    List<ProductImagesResponseDto> deleteProductImages(Long productId, List<Long> imageIds);

    /**
     * Recupera una singola immagine di un prodotto
     *
     * @param productId ID del prodotto
     * @param imageId   ID dell'immagine
     * @return DTO dell'immagine richiesta
     * @throws RuntimeException se l'immagine non esiste
     */
    ProductImagesResponseDto getImageById(Long productId, Long imageId);

    /**
     * Aggiorna lo stato di copertina di un'immagine
     *
     * @param productId ID del prodotto
     * @param imageId   ID dell'immagine
     * @param isCover   True se l'immagine deve essere impostata come copertina
     * @return DTO dell'immagine aggiornata
     * @throws RuntimeException se l'immagine non esiste
     */
    ProductImagesResponseDto updateImageCoverStatus(Long productId, Long imageId, Boolean isCover);

    /**
     * Recupera l'immagine di copertina di un prodotto
     *
     * @param productId ID del prodotto
     * @return DTO dell'immagine di copertina
     * @throws RuntimeException se non ci sono immagini per il prodotto
     */
    ProductImagesResponseDto getCoverImage(Long productId);

    /**
     * Conta il numero di immagini di un prodotto
     *
     * @param productId ID del prodotto
     * @return Numero di immagini associate al prodotto
     */
    long countImagesByProductId(Long productId);

    /**
     * Riordina le immagini di un prodotto secondo l'ordine specificato
     *
     * @param productId       ID del prodotto
     * @param imageIdsInOrder Lista degli ID delle immagini nell'ordine desiderato
     * @return Lista delle immagini riordinate
     * @throws ProductNotFoundException se il prodotto non esiste
     */
    List<ProductImagesResponseDto> reorderImages(Long productId, List<Long> imageIdsInOrder);

    /**
     * Verifica se un prodotto ha immagini
     *
     * @param productId ID del prodotto
     * @return True se il prodotto ha almeno un'immagine
     */
    default boolean hasImages(Long productId) {
        return countImagesByProductId(productId) > 0;
    }

    /**
     * Verifica se un prodotto ha un'immagine di copertina
     *
     * @param productId ID del prodotto
     * @return True se il prodotto ha un'immagine di copertina
     */
    default boolean hasCoverImage(Long productId) {
        try {
            getCoverImage(productId);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
