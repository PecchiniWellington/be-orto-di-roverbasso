package com.ortoroverbasso.ortorovebasso.service.images.impl;

import java.io.IOException;
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
import com.ortoroverbasso.ortorovebasso.exception.ImageNotFoundException;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.images.ImagesMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_images.ProductImagesMapper;
import com.ortoroverbasso.ortorovebasso.repository.images.ImagesDetailRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_images.ProductImagesRepository;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;

import jakarta.transaction.Transactional;

@Service
public class ImageDetailServiceImpl implements IImagesDetailService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private B2StorageClientImpl b2StorageClient;
    @Autowired
    private ProductImagesRepository productImageRepository;

    @Value("${backblaze.bucket-name}")
    private String bucketName;

    @Value("${backblaze.bucket-id}")
    private String bucketId;

    @Value("${app.upload.max-file-size-bytes}")
    private long maxFileSize;

    @Autowired
    private ImagesDetailRepository imagesDetailRepository;

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
        try {
            if (file.getSize() > maxFileSize) {
                throw new IllegalArgumentException("File troppo grande.");
            }

            String filename = file.getOriginalFilename();
            byte[] fileBytes = file.getBytes();
            B2ContentSource contentSource = B2ByteArrayContentSource.builder(fileBytes).build();

            B2UploadFileRequest request = B2UploadFileRequest
                    .builder(bucketId, filename, file.getContentType(), contentSource)
                    .build();

            var response = b2StorageClient.uploadSmallFile(request);

            String fileUrl = "https://f003.backblazeb2.com/file/" + bucketName + "/" + filename;

            ImagesDetailEntity imageEntity = new ImagesDetailEntity();
            imageEntity.setName(filename);
            imageEntity.setUrl(fileUrl);
            imageEntity.setFileId(response.getFileId()); // Salva il fileId
            ImagesDetailEntity savedImage = imagesDetailRepository.save(imageEntity);

            return ImagesMapper.toResponse(savedImage);

        } catch (IOException | B2Exception e) {
            throw new RuntimeException("Errore durante l'upload su B2", e);
        }
    }

    @Override
    public List<ImagesDetailDto> uploadImages(List<MultipartFile> files) {
        List<ImagesDetailDto> uploadedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            uploadedImages.add(uploadImage(file));
        }
        return uploadedImages;
    }

    @Override
    public void deleteImage(Long imageId) {
        try {
            ImagesDetailEntity image = findImageOrThrow(imageId);
            String filename = image.getName();
            String fileId = image.getFileId(); // Ottieni il fileId da ImageEntity

            if (fileId == null || fileId.isBlank()) {
                throw new RuntimeException("fileId mancante per l'immagine con ID: " + imageId);
            }

            B2DeleteFileVersionRequest deleteRequest = B2DeleteFileVersionRequest
                    .builder(filename, fileId)
                    .build();

            b2StorageClient.deleteFileVersion(deleteRequest);
            imagesDetailRepository.delete(image);

            System.out.println("✅ File eliminato da B2 (via fileId): " + filename);

        } catch (B2Exception e) {
            System.err.println("Errore durante la cancellazione su B2: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Errore durante la cancellazione su B2", e);
        }
    }

    @Transactional
    @Override
    public void deleteImages(List<Long> imageIds) {
        for (Long id : imageIds) {
            deleteImage(id);
        }
    }

    @Transactional
    @Override
    public List<ProductImagesResponseDto> connectImageToProduct(ImageConnectRequestDto imagesRequestDto) {
        ProductEntity product = findProductOrThrow(imagesRequestDto.getIdConnect());
        List<ProductImagesResponseDto> connected = new ArrayList<>();

        for (Long imageId : imagesRequestDto.getImageIds()) {
            ImagesDetailEntity imageEntity = findImageOrThrow(imageId);
            ImagesDetailDto imageDto = ImagesMapper.toResponse(imageEntity);

            ProductImageEntity entity = ProductImagesMapper.toEntity(
                    imageDto,
                    imageDto.getName(),
                    imageDto.getUrl(),
                    product.getId());

            productImageRepository.save(entity);
            imagesDetailRepository.delete(imageEntity);
            connected.add(ProductImagesMapper.toResponse(entity));
        }

        return connected;
    }

    @Transactional
    @Override
    public ImageConnectResponse disconnectImagesFromProduct(ImageConnectRequestDto imagesRequestDto) {
        Long productId = imagesRequestDto.getIdConnect();
        List<Long> imageIds = imagesRequestDto.getImageIds();
        List<ImagesDetailDto> restoredImages = new ArrayList<>();

        for (Long imageId : imageIds) {
            ProductImageEntity productImage = productImageRepository.findByProductIdAndId(productId, imageId);
            if (productImage != null) {
                ImagesDetailEntity restored = new ImagesDetailEntity();
                restored.setName(productImage.getName());
                restored.setUrl(productImage.getUrl());
                restored.setIsCover(productImage.isCover());
                restored.setLogo(productImage.isLogo());
                restored.setWhiteBackground(productImage.isWhiteBackground());
                restored.setPosition(productImage.getPosition());
                restored.setEnergyEfficiency(productImage.getEnergyEfficiency());
                restored.setIcon(productImage.getIcon());
                restored.setMarketingPhoto(productImage.getMarketingPhoto());
                restored.setPackagingPhoto(productImage.getPackagingPhoto());
                restored.setBrand(productImage.getBrand());
                restored.setGpsrLabel(productImage.isGpsrLabel());
                restored.setGpsrWarning(productImage.isGpsrWarning());

                imagesDetailRepository.save(restored);
                productImageRepository.delete(productImage);

                restoredImages.add(ImagesMapper.toResponse(restored));
            }
        }

        String message = restoredImages.isEmpty()
                ? "Nessuna immagine disconnessa"
                : restoredImages.size() + " immagini disconnesse correttamente";

        return new ImageConnectResponse(message, restoredImages);
    }

    @Override
    public ImagesDetailEntity uploadAndReturnEntity(MultipartFile file) {
        ImagesDetailDto dto = uploadImage(file);
        return imagesDetailRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Errore dopo upload"));
    }

    @Override
    public void deleteFileFromB2(String fileName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFileFromB2'");
    }

    @Override
    public byte[] downloadImage(String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'downloadImage'");
    }

}
