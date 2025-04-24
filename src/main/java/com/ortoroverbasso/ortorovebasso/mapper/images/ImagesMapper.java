package com.ortoroverbasso.ortorovebasso.mapper.images;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.entity.images.ImagesDetailEntity;

public class ImagesMapper {

    public static ImagesDetailEntity toEntity(ImagesDetailDto imageRequestDto) {

        ImagesDetailEntity imagesDetailEntity = new ImagesDetailEntity();

        imagesDetailEntity.setIsCover(imageRequestDto.isCover());
        imagesDetailEntity.setName(imageRequestDto.getName());
        imagesDetailEntity.setUrl(imageRequestDto.getUrl());
        imagesDetailEntity.setLogo(imageRequestDto.isLogo());
        imagesDetailEntity.setWhiteBackground(imageRequestDto.isWhiteBackground());
        imagesDetailEntity.setPosition(imageRequestDto.getPosition());
        imagesDetailEntity.setEnergyEfficiency(imageRequestDto.getEnergyEfficiency());
        imagesDetailEntity.setIcon(imageRequestDto.getIcon());
        imagesDetailEntity.setMarketingPhoto(imageRequestDto.getMarketingPhoto());
        imagesDetailEntity.setPackagingPhoto(imageRequestDto.getPackagingPhoto());
        imagesDetailEntity.setBrand(imageRequestDto.getBrand());
        imagesDetailEntity.setGpsrLabel(imageRequestDto.isGpsrLabel());
        imagesDetailEntity.setGpsrWarning(imageRequestDto.isGpsrWarning());

        return imagesDetailEntity;

    }

    public static ImagesDetailDto toResponse(ImagesDetailEntity imagesDetailEntity) {

        ImagesDetailDto imagesDetailDto = new ImagesDetailDto();
        imagesDetailDto.setId(imagesDetailEntity.getId());
        imagesDetailDto.setIsCover(imagesDetailEntity.isCover());
        imagesDetailDto.setName(imagesDetailEntity.getName());
        imagesDetailDto.setUrl(imagesDetailEntity.getUrl());
        imagesDetailDto.setLogo(imagesDetailEntity.isLogo());
        imagesDetailDto.setWhiteBackground(imagesDetailEntity.isWhiteBackground());
        imagesDetailDto.setPosition(imagesDetailEntity.getPosition());
        imagesDetailDto.setEnergyEfficiency(imagesDetailEntity.getEnergyEfficiency());
        imagesDetailDto.setIcon(imagesDetailEntity.getIcon());
        imagesDetailDto.setMarketingPhoto(imagesDetailEntity.getMarketingPhoto());
        imagesDetailDto.setPackagingPhoto(imagesDetailEntity.getPackagingPhoto());
        imagesDetailDto.setBrand(imagesDetailEntity.getBrand());
        imagesDetailDto.setGpsrLabel(imagesDetailEntity.isGpsrLabel());
        imagesDetailDto.setGpsrWarning(imagesDetailEntity.isGpsrWarning());

        return imagesDetailDto;
    }

}
