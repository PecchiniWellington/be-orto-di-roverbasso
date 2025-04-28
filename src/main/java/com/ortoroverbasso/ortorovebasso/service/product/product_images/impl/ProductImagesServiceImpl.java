package com.ortoroverbasso.ortorovebasso.service.product.product_images.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImageConnectRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_images.ProductImagesMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_images.ProductImagesRepository;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;
import com.ortoroverbasso.ortorovebasso.service.product.product_images.IProductImagesService;

import jakarta.transaction.Transactional;

@Service
public class ProductImagesServiceImpl implements IProductImagesService {

    @Autowired
    private IImagesDetailService imagesDetailService;

    @Autowired
    private ProductImagesRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Override
    public List<ProductImagesResponseDto> uploadProductImage(
            Long productId,
            MultipartFile request) {

        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " does not exist."));

        ImagesDetailDto productImage = imagesDetailService.uploadImage(request);
        if (productImage == null) {
            throw new IllegalArgumentException("Failed to upload image.");
        }

        ImageConnectRequestDto imageConnectRequest = new ImageConnectRequestDto(productId,
                List.of(productImage.getId()));

        List<ProductImagesResponseDto> connectedImage = imagesDetailService.connectImageToProduct(imageConnectRequest);

        return connectedImage;
    }

    @Override
    public List<ProductImagesResponseDto> getImagesByProductId(Long productId) {
        return productImageRepository.findByProductId(productId).stream()
                .map(ProductImagesMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductImagesResponseDto> deleteProductImages(Long productId, List<Long> imageIds) {
        List<ProductImagesResponseDto> deleted = new ArrayList<>();

        for (Long id : imageIds) {
            ProductImageEntity entity = productImageRepository.findByProductIdAndId(productId, id);
            if (entity != null) {
                productImageRepository.delete(entity);
                deleted.add(ProductImagesMapper.toResponse(entity));
            }
        }

        return deleted;
    }
}
