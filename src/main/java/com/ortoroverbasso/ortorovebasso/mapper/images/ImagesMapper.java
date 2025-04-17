package com.ortoroverbasso.ortorovebasso.mapper.images;

import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.images.ImagesResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.images.ImagesDetailEntity;
import com.ortoroverbasso.ortorovebasso.entity.images.ImagesEntity;

public class ImagesMapper {

    public static ImagesEntity toEntity(ImagesRequestDto userRequestDto) {
        ImagesEntity imagesEntity = new ImagesEntity();

        imagesEntity.setId(userRequestDto.getId());

        List<ImagesDetailEntity> imagesDetailEntities = userRequestDto.getImages().stream()
                .map(imageDto -> {
                    ImagesDetailEntity imagesDetailEntity = new ImagesDetailEntity();
                    imagesDetailEntity.setIsCover(imageDto.isCover());
                    imagesDetailEntity.setName(imageDto.getName());
                    imagesDetailEntity.setUrl(imageDto.getUrl());
                    imagesDetailEntity.setLogo(imageDto.isLogo());
                    imagesDetailEntity.setWhiteBackground(imageDto.isWhiteBackground());
                    imagesDetailEntity.setPosition(imageDto.getPosition());
                    imagesDetailEntity.setEnergyEfficiency(imageDto.getEnergyEfficiency());
                    imagesDetailEntity.setIcon(imageDto.getIcon());
                    imagesDetailEntity.setMarketingPhoto(imageDto.getMarketingPhoto());
                    imagesDetailEntity.setPackagingPhoto(imageDto.getPackagingPhoto());
                    imagesDetailEntity.setBrand(imageDto.getBrand());
                    imagesDetailEntity.setGpsrLabel(imageDto.isGpsrLabel());
                    imagesDetailEntity.setGpsrWarning(imageDto.isGpsrWarning());

                    return imagesDetailEntity;
                })
                .collect(Collectors.toList());

        imagesEntity.setImages(imagesDetailEntities);

        return imagesEntity;
    }

    public static ImagesResponseDto toResponse(ImagesEntity imagesEntity) {
        ImagesResponseDto imagesResponse = new ImagesResponseDto();

        imagesResponse.setId(imagesEntity.getId());

        List<ImagesDetailDto> imagesDetailDtos = imagesEntity.getImages().stream()
                .map(imageDetailEntity -> {
                    ImagesDetailDto imagesDetailDto = new ImagesDetailDto();
                    imagesDetailDto.setId(imageDetailEntity.getId());
                    imagesDetailDto.setIsCover(imageDetailEntity.isCover());
                    imagesDetailDto.setName(imageDetailEntity.getName());
                    imagesDetailDto.setUrl(imageDetailEntity.getUrl());
                    imagesDetailDto.setLogo(imageDetailEntity.isLogo());
                    imagesDetailDto.setWhiteBackground(imageDetailEntity.isWhiteBackground());
                    imagesDetailDto.setPosition(imageDetailEntity.getPosition());
                    imagesDetailDto.setEnergyEfficiency(imageDetailEntity.getEnergyEfficiency());
                    imagesDetailDto.setIcon(imageDetailEntity.getIcon());
                    imagesDetailDto.setMarketingPhoto(imageDetailEntity.getMarketingPhoto());
                    imagesDetailDto.setPackagingPhoto(imageDetailEntity.getPackagingPhoto());
                    imagesDetailDto.setBrand(imageDetailEntity.getBrand());
                    imagesDetailDto.setGpsrLabel(imageDetailEntity.isGpsrLabel());
                    imagesDetailDto.setGpsrWarning(imageDetailEntity.isGpsrWarning());
                    return imagesDetailDto;
                })
                .collect(Collectors.toList());

        imagesResponse.setImages(imagesDetailDtos);
        return imagesResponse;
    }

}
