package com.ortoroverbasso.ortorovebasso.mapper.tags;

import com.ortoroverbasso.ortorovebasso.dto.tags.TagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.TagsResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.tags.TagsEntity;

public class TagsMapper {

    // Mapper da Entity a DTO
    public static TagsResponseDto toDto(TagsEntity tagsEntity) {
        TagsResponseDto tagsDto = new TagsResponseDto();
        tagsDto.setId(tagsEntity.getId());
        tagsDto.setName(tagsEntity.getName());
        tagsDto.setLinkRewrite(tagsEntity.getLinkRewrite());
        tagsDto.setLanguage(tagsEntity.getLanguage());
        return tagsDto;
    }

    // Mapper da DTO a Entity
    public static TagsEntity toEntity(TagsRequestDto tagsRequestDto) {
        TagsEntity tagsEntity = new TagsEntity();
        tagsEntity.setId(tagsRequestDto.getId());
        tagsEntity.setName(tagsRequestDto.getName());
        tagsEntity.setLinkRewrite(tagsRequestDto.getLinkRewrite());
        tagsEntity.setLanguage(tagsRequestDto.getLanguage());
        return tagsEntity;
    }
}
