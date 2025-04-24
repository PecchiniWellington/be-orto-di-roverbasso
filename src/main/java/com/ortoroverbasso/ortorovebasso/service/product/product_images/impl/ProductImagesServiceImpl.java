package com.ortoroverbasso.ortorovebasso.service.product.product_images.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_images.ProductImagesMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.product_images.ProductImagesRepository;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesService;
import com.ortoroverbasso.ortorovebasso.service.product.product_images.IProductImagesService;
import com.ortoroverbasso.ortorovebasso.utils.FileUtils;

@Service
public class ProductImagesServiceImpl implements IProductImagesService {

    @Autowired
    private IImagesService imagesService;

    @Autowired
    private ProductImagesRepository productImageRepository;

    @Override
    public ProductImagesResponseDto uploadProductImage(Long productId, MultipartFile file, ImagesDetailDto request) {
        ImagesResponseDto urlResponse = imagesService.uploadImage(file, request);
        String filename = FileUtils.normalizeFilename(file);

        // Se in futuro ci fossero più immagini, iteriamo sulle immagini
        ImagesDetailDto uploadedImage = urlResponse.getImages().get(0);

        ImagesDetailDto productRequest = new ImagesDetailDto();
        productRequest.setIsCover(request.isCover());
        productRequest.setLogo(request.isLogo());
        productRequest.setWhiteBackground(request.isWhiteBackground());
        productRequest.setPosition(request.getPosition());
        productRequest.setEnergyEfficiency(request.getEnergyEfficiency());
        productRequest.setIcon(request.getIcon());
        productRequest.setMarketingPhoto(request.getMarketingPhoto());
        productRequest.setPackagingPhoto(request.getPackagingPhoto());
        productRequest.setBrand(request.getBrand());
        productRequest.setGpsrLabel(request.isGpsrLabel());
        productRequest.setGpsrWarning(request.isGpsrWarning());

        ProductImageEntity entity = ProductImagesMapper.toEntity(productRequest, filename, uploadedImage.getUrl(),
                productId);
        productImageRepository.save(entity);

        return ProductImagesMapper.toResponse(entity);
    }

    @Override
    public List<ProductImagesResponseDto> getImagesByProductId(Long productId) {
        return productImageRepository.findByProductId(productId).stream()
                .map(ProductImagesMapper::toResponse)
                .collect(Collectors.toList());
    }
}
