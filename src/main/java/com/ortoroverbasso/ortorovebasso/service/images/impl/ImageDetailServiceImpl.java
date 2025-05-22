package com.ortoroverbasso.ortorovebasso.service.images.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

import jakarta.transaction.Transactional;

@Service
public class ImageDetailServiceImpl implements IImagesDetailService {

    private static final String BACKBLAZE_BASE_URL = "https://f003.backblazeb2.com/file/";
    private static final String FILE_TOO_LARGE_MSG = "File troppo grande.";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private B2StorageClientImpl b2StorageClient;
    @Autowired
    private ProductImagesRepository productImageRepository;
    @Autowired
    private ImagesDetailRepository imagesDetailRepository;

    @Value("${backblaze.bucket-name}")
    private String bucketName;
    @Value("${backblaze.bucket-id}")
    private String bucketId;
    @Value("${app.upload.max-file-size-bytes}")
    private long maxFileSize;

    private ImagesDetailEntity findImageOrThrow(Long id) {
        return imagesDetailRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    private ProductEntity findProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public ImagesDetailDto uploadImage(MultipartFile file) {
        return uploadImage(file, null);
    }

    @Override
    public ImagesDetailDto uploadImage(MultipartFile file, String customFileName) {
        try {
            if (file.getSize() > maxFileSize) {
                throw new IllegalArgumentException("File troppo grande.");
            }

            // Prendi il content type reale del file
            String contentType = file.getContentType();
            String extension = ImageExtension.getExtensionForMimeType(contentType);

            // originalName dal MultipartFile
            String originalName = file.getOriginalFilename();

            // Costruisci nome file con estensione coerente
            String baseName = (customFileName != null && !customFileName.isBlank())
                    ? customFileName
                    : (originalName != null ? originalName : "file");

            int dotIndex = baseName.lastIndexOf('.');
            if (dotIndex > 0) {
                baseName = baseName.substring(0, dotIndex);
            }

            String filename = baseName + extension;

            byte[] fileBytes = file.getBytes();
            B2ContentSource contentSource = B2ByteArrayContentSource.builder(fileBytes).build();

            B2UploadFileRequest request = B2UploadFileRequest
                    .builder(bucketId, filename, contentType, contentSource)
                    .build();

            var response = b2StorageClient.uploadSmallFile(request);

            String fileUrl = BACKBLAZE_BASE_URL + bucketName + "/" + filename;

            ImagesDetailEntity imageEntity = new ImagesDetailEntity();
            imageEntity.setName(filename);
            imageEntity.setUrl(fileUrl);
            imageEntity.setFileId(response.getFileId());
            ImagesDetailEntity savedImage = imagesDetailRepository.save(imageEntity);

            System.out.println("🚀 Upload su Backblaze: " + filename + " - dimensione: " + file.getSize());
            System.out.println("✅ Immagine salvata: " + fileUrl + " - ID DB: " + savedImage.getId());

            return ImagesMapper.toResponse(savedImage);

        } catch (IOException | B2Exception e) {
            throw new RuntimeException("Errore durante l'upload su B2", e);
        }
    }

    @Override
    public List<ImagesDetailDto> uploadImages(List<MultipartFile> files) {
        return files.stream()
                .map(this::uploadImage)
                .toList();
    }

    @Override
    public void deleteImage(Long imageId) {
        try {
            ImagesDetailEntity image = findImageOrThrow(imageId);
            if (image.getFileId() == null || image.getFileId().isBlank())
                throw new RuntimeException("fileId mancante per l'immagine con ID: " + imageId);

            var deleteRequest = B2DeleteFileVersionRequest.builder(image.getName(), image.getFileId()).build();

            b2StorageClient.deleteFileVersion(deleteRequest);
            imagesDetailRepository.delete(image);

            System.out.println("✅ File eliminato da B2 (via fileId): " + image.getName());
        } catch (B2Exception e) {
            System.err.println("Errore durante la cancellazione su B2: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Errore durante la cancellazione su B2", e);
        }
    }

    @Transactional
    @Override
    public void deleteImages(List<Long> imageIds) {
        imageIds.forEach(this::deleteImage);
    }

    @Transactional
    @Override
    public List<ProductImagesResponseDto> connectImageToProduct(ImageConnectRequestDto dto) {
        ProductEntity product = findProductOrThrow(dto.getIdConnect());
        List<ProductImagesResponseDto> connected = new ArrayList<>();

        for (Long imageId : dto.getImageIds()) {
            ImagesDetailEntity imageEntity = findImageOrThrow(imageId);
            ImagesDetailDto imageDto = ImagesMapper.toResponse(imageEntity);

            ProductImageEntity productImage = ProductImagesMapper.toEntity(imageDto, imageDto.getName(),
                    imageDto.getUrl(), product.getId());
            productImageRepository.save(productImage);
            imagesDetailRepository.delete(imageEntity);

            connected.add(ProductImagesMapper.toResponse(productImage));
        }
        return connected;
    }

    @Transactional
    @Override
    public ImageConnectResponse disconnectImagesFromProduct(ImageConnectRequestDto dto) {
        Long productId = dto.getIdConnect();
        List<Long> imageIds = dto.getImageIds();
        List<ImagesDetailDto> restoredImages = new ArrayList<>();

        for (Long imageId : imageIds) {
            ProductImageEntity productImage = productImageRepository.findByProductIdAndId(productId, imageId);
            if (productImage == null)
                continue;

            ImagesDetailEntity restored = new ImagesDetailEntity();
            copyProductImageToImageEntity(productImage, restored);

            imagesDetailRepository.save(restored);
            productImageRepository.delete(productImage);

            restoredImages.add(ImagesMapper.toResponse(restored));
        }

        String message = restoredImages.isEmpty()
                ? "Nessuna immagine disconnessa"
                : restoredImages.size() + " immagini disconnesse correttamente";

        return new ImageConnectResponse(message, restoredImages);
    }

    @Override
    public ImagesDetailEntity uploadFromUrl(String imageUrl, String customName) {
        try {
            URL url = new URL(imageUrl);
            var connection = url.openConnection();
            connection.connect();

            String contentType = connection.getContentType();
            // Usa enum per ricavare estensione corretta dal contentType
            String extension = ImageExtension.getExtensionForMimeType(contentType);

            // Se l'estensione non è affidabile, prova a estrarla dall'URL
            if (extension == null || extension.isBlank() || extension.equals(".jpg")) {
                extension = ImageExtensionUtil.extractExtensionFromUrl(imageUrl);
            }

            // Costruisci baseName coerente, senza doppie estensioni
            String baseName;
            if (customName != null && !customName.isBlank()) {
                int dotIndex = customName.lastIndexOf('.');
                baseName = dotIndex > 0 ? customName.substring(0, dotIndex) : customName;
            } else {
                baseName = ImageExtensionUtil.extractBaseNameFromUrl(imageUrl);
            }
            if (baseName.isBlank()) {
                baseName = "downloaded-image";
            }

            String fileName = baseName + extension;

            // Scarica i byte dell'immagine
            byte[] bytes;
            try (var inputStream = connection.getInputStream()) {
                bytes = inputStream.readAllBytes();
            }

            System.out.println("📥 Scaricata immagine da: " + imageUrl + " - dimensione: " + bytes.length);

            // Crea MultipartFile usando BytesMultipartFile (assumendo contentType corretto)
            MultipartFile multipartFile = new BytesMultipartFile(fileName, bytes, contentType);

            // Usa la funzione uploadAndReturnEntity modificata per passare il nome custom
            // corretto e contentType coerente
            return uploadAndReturnEntity(multipartFile, baseName);

        } catch (IOException e) {
            throw new RuntimeException("Errore durante il download dell'immagine da URL", e);
        }
    }

    @Override
    public ImagesDetailEntity uploadAndReturnEntity(MultipartFile file, String customFileName) {
        ImagesDetailDto dto = uploadImage(file, customFileName);
        return imagesDetailRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Errore dopo upload"));
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
        } catch (B2Exception e) {
            throw new RuntimeException("Impossibile cancellare file da Backblaze B2", e);
        }
    }

    @Override
    public byte[] downloadImage(String filename) {
        throw new UnsupportedOperationException("Unimplemented method 'downloadImage'");
    }

    // --- Metodi privati per ridurre duplicazioni e aumentare chiarezza ---

    private void checkFileSize(MultipartFile file) {
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException(FILE_TOO_LARGE_MSG);
        }
    }

    private void logUpload(String filename, long size, String url, Long id) {
        System.out.println("🚀 Upload su Backblaze: " + filename + " - dimensione: " + size);
        System.out.println("✅ Immagine salvata: " + url + " - ID DB: " + id);
    }

    private void copyProductImageToImageEntity(ProductImageEntity productImage, ImagesDetailEntity imageEntity) {
        imageEntity.setName(productImage.getName());
        imageEntity.setUrl(productImage.getUrl());
        imageEntity.setIsCover(productImage.isCover());
        imageEntity.setLogo(productImage.isLogo());
        imageEntity.setWhiteBackground(productImage.isWhiteBackground());
        imageEntity.setPosition(productImage.getPosition());
        imageEntity.setEnergyEfficiency(productImage.getEnergyEfficiency());
        imageEntity.setIcon(productImage.getIcon());
        imageEntity.setMarketingPhoto(productImage.getMarketingPhoto());
        imageEntity.setPackagingPhoto(productImage.getPackagingPhoto());
        imageEntity.setBrand(productImage.getBrand());
        imageEntity.setGpsrLabel(productImage.isGpsrLabel());
        imageEntity.setGpsrWarning(productImage.isGpsrWarning());
    }
}
