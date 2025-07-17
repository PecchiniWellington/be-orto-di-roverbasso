package com.ortoroverbasso.ortorovebasso.service.images.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.backblaze.b2.client.B2StorageClientImpl;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2DeleteFileVersionRequest;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import com.ortoroverbasso.ortorovebasso.dto.images.ImageConnectRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.images.ImageConnectResponse;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.images.ImagesDetailEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;
import com.ortoroverbasso.ortorovebasso.enums.ImageExtension;
import com.ortoroverbasso.ortorovebasso.exception.ImageNotFoundException;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.images.ImagesMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_images.ProductImagesMapper;
import com.ortoroverbasso.ortorovebasso.repository.images.ImagesDetailRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_images.ProductImagesRepository;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;
import com.ortoroverbasso.ortorovebasso.utils.BytesMultipartFile;
import com.ortoroverbasso.ortorovebasso.utils.ImageExtensionUtil;

@Service
@Transactional
public class ImageDetailServiceImpl implements IImagesDetailService {

    private static final Logger logger = LoggerFactory.getLogger(ImageDetailServiceImpl.class);
    private static final String BACKBLAZE_BASE_URL = "https://f003.backblazeb2.com/file/";
    private static final String FILE_TOO_LARGE_MSG = "File troppo grande.";

    private final ProductRepository productRepository;
    private final B2StorageClientImpl b2StorageClient;
    private final ProductImagesRepository productImageRepository;
    private final ImagesDetailRepository imagesDetailRepository;

    @Value("${backblaze.bucket-name}")
    private String bucketName;

    @Value("${backblaze.bucket-id}")
    private String bucketId;

    @Value("${app.upload.max-file-size-bytes}")
    private long maxFileSize;

    @Autowired
    public ImageDetailServiceImpl(
            ProductRepository productRepository,
            B2StorageClientImpl b2StorageClient,
            ProductImagesRepository productImageRepository,
            ImagesDetailRepository imagesDetailRepository) {
        this.productRepository = productRepository;
        this.b2StorageClient = b2StorageClient;
        this.productImageRepository = productImageRepository;
        this.imagesDetailRepository = imagesDetailRepository;
    }

    private ImagesDetailEntity findImageOrThrow(Long id) {
        return imagesDetailRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image not found with ID: " + id));
    }

    private ProductEntity findProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public ImagesDetailDto uploadImage(MultipartFile file) {
        return uploadImage(file, null);
    }

    @Override
    public ImagesDetailDto uploadImage(MultipartFile file, String customFileName) {
        try {
            checkFileSize(file);

            String contentType = file.getContentType();
            String extension = ImageExtension.getExtensionForMimeType(contentType);
            String originalName = file.getOriginalFilename();

            // Costruisci nome file con estensione coerente
            String baseName = buildBaseName(customFileName, originalName);
            String filename = baseName + extension;

            byte[] fileBytes = file.getBytes();
            B2ContentSource contentSource = B2ByteArrayContentSource.builder(fileBytes).build();

            B2UploadFileRequest request = B2UploadFileRequest
                    .builder(bucketId, filename, contentType, contentSource)
                    .build();

            var response = b2StorageClient.uploadSmallFile(request);
            String fileUrl = BACKBLAZE_BASE_URL + bucketName + "/" + filename;

            // Crea e salva l'entità usando i metodi corretti
            ImagesDetailEntity imageEntity = createImageEntity(filename, fileUrl, response.getFileId());
            ImagesDetailEntity savedImage = imagesDetailRepository.save(imageEntity);

            logUpload(filename, file.getSize(), fileUrl, savedImage.getId());

            return ImagesMapper.toResponse(savedImage);

        } catch (IOException | B2Exception e) {
            logger.error("Errore durante l'upload su B2: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante l'upload su B2", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImagesDetailDto> uploadImages(List<MultipartFile> files) {
        return files.stream()
                .map(this::uploadImage)
                .toList();
    }

    @Override
    public void deleteImage(Long imageId) {
        try {
            ImagesDetailEntity image = findImageOrThrow(imageId);

            if (image.getFileId() == null || image.getFileId().isBlank()) {
                throw new RuntimeException("fileId mancante per l'immagine con ID: " + imageId);
            }

            var deleteRequest = B2DeleteFileVersionRequest.builder(image.getName(), image.getFileId()).build();
            b2StorageClient.deleteFileVersion(deleteRequest);
            imagesDetailRepository.delete(image);

            logger.info("File eliminato da B2: {}", image.getName());

        } catch (B2Exception e) {
            logger.error("Errore durante la cancellazione su B2: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante la cancellazione su B2", e);
        }
    }

    @Override
    public void deleteImages(List<Long> imageIds) {
        imageIds.forEach(this::deleteImage);
    }

    @Override
    public List<ProductImagesResponseDto> connectImageToProduct(ImageConnectRequestDto dto) {
        ProductEntity product = findProductOrThrow(dto.getIdConnect());
        List<ProductImagesResponseDto> connected = new ArrayList<>();

        for (Long imageId : dto.getImageIds()) {
            ImagesDetailEntity imageEntity = findImageOrThrow(imageId);

            // Crea ProductImageEntity usando il mapper corretto
            ProductImageEntity productImage = createProductImageFromDetail(imageEntity, product);
            ProductImageEntity savedProductImage = productImageRepository.save(productImage);

            // Elimina l'immagine dalla tabella dettagli
            imagesDetailRepository.delete(imageEntity);

            // Usa il mapper corretto per la risposta
            ProductImagesResponseDto responseDto = ProductImagesMapper.toResponseDto(savedProductImage);
            connected.add(responseDto);
        }

        logger.info("Connesse {} immagini al prodotto {}", connected.size(), dto.getIdConnect());
        return connected;
    }

    @Override
    public ImageConnectResponse disconnectImagesFromProduct(ImageConnectRequestDto dto) {
        Long productId = dto.getIdConnect();
        List<Long> imageIds = dto.getImageIds();
        List<ImagesDetailDto> restoredImages = new ArrayList<>();

        for (Long imageId : imageIds) {
            // Trova l'immagine del prodotto
            ProductImageEntity productImage = productImageRepository.findByProductIdAndId(productId, imageId);
            if (productImage == null) {
                logger.warn("ProductImage non trovata per productId {} e imageId {}", productId, imageId);
                continue;
            }

            // Crea una nuova ImagesDetailEntity
            ImagesDetailEntity restored = createImageDetailFromProduct(productImage);
            imagesDetailRepository.save(restored);

            // Elimina dalla tabella prodotto
            productImageRepository.delete(productImage);

            restoredImages.add(ImagesMapper.toResponse(restored));
        }

        String message = restoredImages.isEmpty()
                ? "Nessuna immagine disconnessa"
                : restoredImages.size() + " immagini disconnesse correttamente";

        logger.info("Disconnesse {} immagini dal prodotto {}", restoredImages.size(), productId);
        return new ImageConnectResponse(message, restoredImages);
    }

    @Override
    public ImagesDetailEntity uploadFromUrl(String imageUrl, String customName) {
        try {
            URL url = new URL(imageUrl);
            var connection = url.openConnection();
            connection.connect();

            String contentType = connection.getContentType();
            String extension = ImageExtension.getExtensionForMimeType(contentType);

            // Se l'estensione non è affidabile, prova a estrarla dall'URL
            if (extension == null || extension.isBlank() || extension.equals(".jpg")) {
                extension = ImageExtensionUtil.extractExtensionFromUrl(imageUrl);
            }

            String baseName = buildBaseNameFromUrl(customName, imageUrl);
            String fileName = baseName + extension;

            // Scarica i byte dell'immagine
            byte[] bytes;
            try (var inputStream = connection.getInputStream()) {
                bytes = inputStream.readAllBytes();
            }

            logger.info("Scaricata immagine da: {} - dimensione: {}", imageUrl, bytes.length);

            // Crea MultipartFile usando BytesMultipartFile
            MultipartFile multipartFile = new BytesMultipartFile(fileName, bytes, contentType);

            return uploadAndReturnEntity(multipartFile, baseName);

        } catch (IOException e) {
            logger.error("Errore durante il download dell'immagine da URL: {}", e.getMessage(), e);
            throw new RuntimeException("Errore durante il download dell'immagine da URL", e);
        }
    }

    @Override
    public ImagesDetailEntity uploadAndReturnEntity(MultipartFile file, String customFileName) {
        ImagesDetailDto dto = uploadImage(file, customFileName);
        return imagesDetailRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Errore dopo upload: entità non trovata"));
    }

    @Override
    public ImagesDetailEntity uploadAndReturnEntity(MultipartFile file) {
        return uploadAndReturnEntity(file, null);
    }

    @Override
    public void deleteFileFromB2(String fileId, String fileName) {
        try {
            b2StorageClient.deleteFileVersion(
                    B2DeleteFileVersionRequest.builder(fileName, fileId).build());
            logger.info("File eliminato da B2: {} ({})", fileName, fileId);
        } catch (B2Exception e) {
            logger.error("Errore durante eliminazione file da B2: {}", e.getMessage(), e);
            throw new RuntimeException("Impossibile cancellare file da Backblaze B2", e);
        }
    }

    @Override
    public byte[] downloadImage(String filename) {
        throw new UnsupportedOperationException("Metodo downloadImage non ancora implementato");
    }

    // --- Metodi privati helper ---

    private void checkFileSize(MultipartFile file) {
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException(FILE_TOO_LARGE_MSG);
        }
    }

    private String buildBaseName(String customFileName, String originalName) {
        String baseName = (customFileName != null && !customFileName.isBlank())
                ? customFileName
                : (originalName != null ? originalName : "file");

        int dotIndex = baseName.lastIndexOf('.');
        return dotIndex > 0 ? baseName.substring(0, dotIndex) : baseName;
    }

    private String buildBaseNameFromUrl(String customName, String imageUrl) {
        if (customName != null && !customName.isBlank()) {
            int dotIndex = customName.lastIndexOf('.');
            return dotIndex > 0 ? customName.substring(0, dotIndex) : customName;
        } else {
            String baseName = ImageExtensionUtil.extractBaseNameFromUrl(imageUrl);
            return baseName.isBlank() ? "downloaded-image" : baseName;
        }
    }

    private ImagesDetailEntity createImageEntity(String filename, String fileUrl, String fileId) {
        ImagesDetailEntity imageEntity = new ImagesDetailEntity();
        imageEntity.setName(filename);
        imageEntity.setUrl(fileUrl);
        imageEntity.setFileId(fileId);
        // Imposta valori di default per i campi Boolean
        imageEntity.setIsCover(false);
        imageEntity.setIsLogo(false);
        imageEntity.setWhiteBackground(false);
        imageEntity.setGpsrLabel(false);
        imageEntity.setGpsrWarning(false);
        imageEntity.setPosition(0);
        return imageEntity;
    }

    private ProductImageEntity createProductImageFromDetail(ImagesDetailEntity imageEntity, ProductEntity product) {
        ProductImageEntity productImage = new ProductImageEntity();
        productImage.setUrl(imageEntity.getUrl());
        productImage.setIsCover(imageEntity.getIsCover());
        productImage.setAltText(imageEntity.getName());
        productImage.setDisplayOrder(imageEntity.getPosition());
        productImage.setProduct(product);
        return productImage;
    }

    private ImagesDetailEntity createImageDetailFromProduct(ProductImageEntity productImage) {
        ImagesDetailEntity restored = new ImagesDetailEntity();
        restored.setName(productImage.getAltText() != null ? productImage.getAltText() : "restored-image");
        restored.setUrl(productImage.getUrl());
        restored.setIsCover(productImage.getIsCover());
        restored.setPosition(productImage.getDisplayOrder());

        // Imposta valori di default per campi non mappati
        restored.setIsLogo(false);
        restored.setWhiteBackground(false);
        restored.setGpsrLabel(false);
        restored.setGpsrWarning(false);

        return restored;
    }

    private void logUpload(String filename, long size, String url, Long id) {
        logger.info("Upload su Backblaze completato: {} - dimensione: {} - URL: {} - ID DB: {}",
                filename, size, url, id);
    }
}
