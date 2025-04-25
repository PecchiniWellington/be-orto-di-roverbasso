package com.ortoroverbasso.ortorovebasso.service.product.product_images.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.product_images.ProductImageEntity;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_images.ProductImagesMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.product_images.ProductImagesRepository;
import com.ortoroverbasso.ortorovebasso.service.images.IImagesDetailService;
import com.ortoroverbasso.ortorovebasso.service.product.product_images.IProductImagesService;
import com.ortoroverbasso.ortorovebasso.utils.FileUtils;

@Service
public class ProductImagesServiceImpl implements IProductImagesService {

    @Autowired
    private IImagesDetailService imagesDetailService;

    @Autowired
    private ProductImagesRepository productImageRepository;

    @Override
    public ProductImagesResponseDto uploadProductImage(
            Long productId,
            MultipartFile file,
            ImagesDetailDto request) {

        ImagesDetailDto urlResponse = imagesDetailService.uploadImage(file);
        String filename = FileUtils.normalizeFilename(file);

        String uploadedImage = urlResponse.getUrl();

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

        ProductImageEntity entity = ProductImagesMapper.toEntity(productRequest, filename, uploadedImage,
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
