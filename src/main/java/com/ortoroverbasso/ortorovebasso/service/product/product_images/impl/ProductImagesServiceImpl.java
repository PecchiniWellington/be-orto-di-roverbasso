package com.ortoroverbasso.ortorovebasso.service.product.product_images.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImageConnectRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_images.ProductImagesMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_images.ProductImagesRepository;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;
import com.ortoroverbasso.ortorovebasso.service.product.product_images.IProductImagesService;

@Service
@Transactional
public class ProductImagesServiceImpl implements IProductImagesService {

    private static final Logger logger = LoggerFactory.getLogger(ProductImagesServiceImpl.class);

    private final IImagesDetailService imagesDetailService;
    private final ProductImagesRepository productImageRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductImagesServiceImpl(
            IImagesDetailService imagesDetailService,
            ProductImagesRepository productImageRepository,
            ProductRepository productRepository) {
        this.imagesDetailService = imagesDetailService;
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductImagesResponseDto> uploadProductImage(Long productId, MultipartFile request) {
        logger.info("Uploading image for product ID: {}", productId);

        // Verifica che il prodotto esista
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        try {
            // Upload dell'immagine nel servizio immagini
            ImagesDetailDto uploadedImage = imagesDetailService.uploadImage(request);

            if (uploadedImage == null || uploadedImage.getId() == null) {
                throw new RuntimeException("Failed to upload image - no ID returned");
            }

            // Connetti l'immagine al prodotto
            ImageConnectRequestDto connectRequest = new ImageConnectRequestDto(
                    productId,
                    List.of(uploadedImage.getId()));

            List<ProductImagesResponseDto> connectedImages = imagesDetailService.connectImageToProduct(connectRequest);

            logger.info("Successfully uploaded and connected {} image(s) to product {}",
                    connectedImages.size(), productId);

            return connectedImages;

        } catch (Exception e) {
            logger.error("Error uploading image for product {}: {}", productId, e.getMessage(), e);
            throw new RuntimeException("Failed to upload image for product: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductImagesResponseDto> getImagesByProductId(Long productId) {
        logger.debug("Retrieving images for product ID: {}", productId);

        // Verifica che il prodotto esista
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

        List<ProductImageEntity> images = productImageRepository.findByProductId(productId);

        return images.stream()
                .map(ProductImagesMapper::toResponseDto) // ✅ Metodo corretto
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductImagesResponseDto> deleteProductImages(Long productId, List<Long> imageIds) {
        logger.info("Deleting {} images from product {}", imageIds.size(), productId);

        // Verifica che il prodotto esista
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

        List<ProductImagesResponseDto> deletedImages = new ArrayList<>();

        for (Long imageId : imageIds) {
            try {
                ProductImageEntity imageEntity = productImageRepository.findByProductIdAndId(productId, imageId);

                if (imageEntity != null) {
                    // Converti in DTO prima di eliminare
                    ProductImagesResponseDto imageDto = ProductImagesMapper.toResponseDto(imageEntity);

                    // Elimina l'entità
                    productImageRepository.delete(imageEntity);

                    // Aggiungi alla lista dei eliminati
                    deletedImages.add(imageDto);

                    logger.debug("Deleted image {} from product {}", imageId, productId);
                } else {
                    logger.warn("Image {} not found for product {}", imageId, productId);
                }

            } catch (Exception e) {
                logger.error("Error deleting image {} from product {}: {}", imageId, productId, e.getMessage());
                // Continua con le altre immagini invece di fermarsi
            }
        }

        logger.info("Successfully deleted {} images from product {}", deletedImages.size(), productId);
        return deletedImages;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductImagesResponseDto getImageById(Long productId, Long imageId) {
        logger.debug("Retrieving image {} for product {}", imageId, productId);

        ProductImageEntity imageEntity = productImageRepository.findByProductIdAndId(productId, imageId);

        if (imageEntity == null) {
            throw new RuntimeException("Image not found with ID: " + imageId + " for product: " + productId);
        }

        return ProductImagesMapper.toResponseDto(imageEntity);
    }

    @Override
    public ProductImagesResponseDto updateImageCoverStatus(Long productId, Long imageId, Boolean isCover) {
        logger.info("Updating cover status for image {} in product {} to: {}", imageId, productId, isCover);

        ProductImageEntity imageEntity = productImageRepository.findByProductIdAndId(productId, imageId);

        if (imageEntity == null) {
            throw new RuntimeException("Image not found with ID: " + imageId + " for product: " + productId);
        }

        // Se stiamo impostando come cover, rimuovi il flag dalle altre immagini
        if (Boolean.TRUE.equals(isCover)) {
            List<ProductImageEntity> otherImages = productImageRepository.findByProductId(productId);
            otherImages.forEach(img -> {
                if (!img.getId().equals(imageId)) {
                    img.setIsCover(false);
                    productImageRepository.save(img);
                }
            });
        }

        // Aggiorna l'immagine corrente
        imageEntity.setIsCover(isCover);
        ProductImageEntity savedImage = productImageRepository.save(imageEntity);

        logger.info("Updated cover status for image {} in product {}", imageId, productId);
        return ProductImagesMapper.toResponseDto(savedImage);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductImagesResponseDto getCoverImage(Long productId) {
        logger.debug("Retrieving cover image for product {}", productId);

        List<ProductImageEntity> images = productImageRepository.findByProductId(productId);

        ProductImageEntity coverImage = images.stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsCover()))
                .findFirst()
                .orElse(images.isEmpty() ? null : images.get(0)); // Fallback alla prima immagine

        if (coverImage == null) {
            throw new RuntimeException("No images found for product: " + productId);
        }

        return ProductImagesMapper.toResponseDto(coverImage);
    }

    @Override
    @Transactional(readOnly = true)
    public long countImagesByProductId(Long productId) {
        return productImageRepository.countByProductId(productId);
    }

    @Override
    public List<ProductImagesResponseDto> reorderImages(Long productId, List<Long> imageIdsInOrder) {
        logger.info("Reordering {} images for product {}", imageIdsInOrder.size(), productId);

        List<ProductImagesResponseDto> reorderedImages = new ArrayList<>();

        for (int i = 0; i < imageIdsInOrder.size(); i++) {
            Long imageId = imageIdsInOrder.get(i);
            ProductImageEntity imageEntity = productImageRepository.findByProductIdAndId(productId, imageId);

            if (imageEntity != null) {
                imageEntity.setDisplayOrder(i);
                ProductImageEntity savedImage = productImageRepository.save(imageEntity);
                reorderedImages.add(ProductImagesMapper.toResponseDto(savedImage));
            }
        }

        logger.info("Reordered {} images for product {}", reorderedImages.size(), productId);
        return reorderedImages;
    }
}
