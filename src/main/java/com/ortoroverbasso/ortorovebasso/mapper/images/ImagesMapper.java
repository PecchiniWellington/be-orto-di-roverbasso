package com.ortoroverbasso.ortorovebasso.mapper.images;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.ortoroverbasso.ortorovebasso.dto.images.ImagesDetailDto;
import com.ortoroverbasso.ortorovebasso.entity.images.ImagesDetailEntity;

public class ImagesMapper {

    /**
     * Converte ImagesDetailDto in ImagesDetailEntity
     */
    public static ImagesDetailEntity toEntity(ImagesDetailDto dto) {
        if (dto == null) {
            return null;
        }

        ImagesDetailEntity entity = new ImagesDetailEntity();

        // Usa i getter che restituiscono Boolean (non i metodi isXxx())
        entity.setIsCover(dto.getIsCover());
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());
        entity.setIsLogo(dto.getIsLogo());
        entity.setWhiteBackground(dto.getWhiteBackground());
        entity.setPosition(dto.getPosition());
        entity.setEnergyEfficiency(dto.getEnergyEfficiency());
        entity.setIcon(dto.getIcon());
        entity.setMarketingPhoto(dto.getMarketingPhoto());
        entity.setPackagingPhoto(dto.getPackagePhoto());
        entity.setBrand(dto.getBrand());
        entity.setGpsrLabel(dto.getGpsrLabel());
        entity.setGpsrWarning(dto.getGpsrWarning());

        // Solo se il metodo getFileId() esiste nel DTO - altrimenti commenta questa
        // riga
        // entity.setFileId(dto.getFileId());

        return entity;
    }

    /**
     * Converte ImagesDetailEntity in ImagesDetailDto
     */
    public static ImagesDetailDto toResponse(ImagesDetailEntity entity) {
        if (entity == null) {
            return null;
        }

        ImagesDetailDto dto = new ImagesDetailDto();

        // Solo se il metodo getId() esiste nell'entity - altrimenti commenta questa
        // riga
        // dto.setId(entity.getId());

        // Usa i getter che restituiscono Boolean dall'entity
        dto.setIsCover(entity.getIsCover());
        dto.setName(entity.getName());
        dto.setUrl(entity.getUrl());
        dto.setIsLogo(entity.getIsLogo());
        dto.setWhiteBackground(entity.getWhiteBackground());
        dto.setPosition(entity.getPosition());
        dto.setEnergyEfficiency(entity.getEnergyEfficiency());
        dto.setIcon(entity.getIcon());
        dto.setMarketingPhoto(entity.getMarketingPhoto());
        dto.setPackagePhoto(entity.getPackagingPhoto());
        dto.setBrand(entity.getBrand());
        dto.setGpsrLabel(entity.getGpsrLabel());
        dto.setGpsrWarning(entity.getGpsrWarning());

        // Solo se il metodo getFileId() esiste nell'entity - altrimenti commenta questa
        // riga
        // dto.setFileId(entity.getFileId());

        return dto;
    }

    /**
     * Converte una lista di entity in lista di DTO
     */
    public static List<ImagesDetailDto> toResponseList(List<ImagesDetailEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(ImagesMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Converte una lista di DTO in lista di entity
     */
    public static List<ImagesDetailEntity> toEntityList(List<ImagesDetailDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return Collections.emptyList();
        }

        return dtos.stream()
                .map(ImagesMapper::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Aggiorna un'entity esistente con i dati dal DTO
     */
    public static void updateEntityFromDto(ImagesDetailEntity entity, ImagesDetailDto dto) {
        if (entity == null || dto == null) {
            return;
        }

        if (dto.getIsCover() != null) {
            entity.setIsCover(dto.getIsCover());
        }

        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            entity.setName(dto.getName().trim());
        }

        if (dto.getUrl() != null && !dto.getUrl().trim().isEmpty()) {
            entity.setUrl(dto.getUrl().trim());
        }

        if (dto.getIsLogo() != null) {
            entity.setIsLogo(dto.getIsLogo());
        }

        if (dto.getWhiteBackground() != null) {
            entity.setWhiteBackground(dto.getWhiteBackground());
        }

        if (dto.getPosition() != null) {
            entity.setPosition(dto.getPosition());
        }

        if (dto.getEnergyEfficiency() != null) {
            entity.setEnergyEfficiency(dto.getEnergyEfficiency());
        }

        if (dto.getIcon() != null) {
            entity.setIcon(dto.getIcon());
        }

        if (dto.getMarketingPhoto() != null) {
            entity.setMarketingPhoto(dto.getMarketingPhoto());
        }

        if (dto.getPackagePhoto() != null) {
            entity.setPackagingPhoto(dto.getPackagePhoto());
        }

        if (dto.getBrand() != null) {
            entity.setBrand(dto.getBrand());
        }

        if (dto.getGpsrLabel() != null) {
            entity.setGpsrLabel(dto.getGpsrLabel());
        }

        if (dto.getGpsrWarning() != null) {
            entity.setGpsrWarning(dto.getGpsrWarning());
        }
    }
}
