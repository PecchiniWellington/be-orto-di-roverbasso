package com.ortoroverbasso.ortorovebasso.mapper.tags;

import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.tags.ProductTagsEntity;
import com.ortoroverbasso.ortorovebasso.entity.tags.TagsEntity;

public class ProductTagsMapper {

    // Mapper da Entity a DTO
    public static ProductTagsResponseDto toResponse(ProductTagsEntity productTagsEntity) {
        ProductTagsResponseDto productTagsResponseDto = new ProductTagsResponseDto();
        productTagsResponseDto.setId(productTagsEntity.getId());
        productTagsResponseDto.setSku(productTagsEntity.getSku());

        productTagsResponseDto.setTag(TagsMapper.toDto(productTagsEntity.getTag()));

        return productTagsResponseDto;
    }

    // Mapper da DTO a Entity
    public static ProductTagsEntity toEntity(ProductTagsRequestDto productTagsRequestDto) {
        ProductTagsEntity productTagsEntity = new ProductTagsEntity();
        productTagsEntity.setId(productTagsRequestDto.getId());
        productTagsEntity.setSku(productTagsRequestDto.getSku());

        TagsEntity tagsEntity = TagsMapper.toEntity(productTagsRequestDto.getTag());
        productTagsEntity.setTag(tagsEntity);

        return productTagsEntity;
    }
}
