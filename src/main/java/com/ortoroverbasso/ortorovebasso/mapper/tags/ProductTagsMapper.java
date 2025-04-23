package com.ortoroverbasso.ortorovebasso.mapper.tags;

import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.TagsResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.tags.ProductTagsEntity;
import com.ortoroverbasso.ortorovebasso.entity.tags.TagsEntity;

public class ProductTagsMapper {

    public static ProductTagsResponseDto toResponseDto(ProductTagsEntity productTagsEntity) {
        if (productTagsEntity == null) {
            return null;
        }

        TagsResponseDto tagResponseDto = new TagsResponseDto(
                productTagsEntity.getTag().getId(),
                productTagsEntity.getTag().getName(),
                productTagsEntity.getTag().getLinkRewrite(),
                productTagsEntity.getTag().getLanguage());

        return new ProductTagsResponseDto(
                productTagsEntity.getProduct().getId(),
                productTagsEntity.getProduct().getSku(),
                tagResponseDto);
    }

    public static ProductTagsEntity toEntity(ProductTagsRequestDto dto, ProductEntity product, TagsEntity tag) {
        if (dto == null || product == null || tag == null) {
            return null;
        }

        ProductTagsEntity entity = new ProductTagsEntity();
        entity.setProduct(product);
        entity.setTag(tag);

        return entity;
    }

}
