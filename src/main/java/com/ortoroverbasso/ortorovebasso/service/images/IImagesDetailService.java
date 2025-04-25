package com.ortoroverbasso.ortorovebasso.service.images;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImageConnectRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.images.ImageConnectResponse;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;

public interface IImagesDetailService {
    // Define the methods that will be implemented in the service class

    ImagesDetailDto uploadImage(MultipartFile file);

    List<ImagesDetailDto> uploadImages(List<MultipartFile> files);

    List<ProductImagesResponseDto> connectImageToProduct(ImageConnectRequestDto imagesRequestDto);

    ImageConnectResponse disconnectImagesFromProduct(ImageConnectRequestDto imagesRequestDto);

    void deleteFileFromB2(String fileName);

    byte[] downloadImage(String filename);

    void deleteImage(Long imageId);

    void deleteImages(List<Long> imageIds);

    // Add any other methods related to image processing or management

}
