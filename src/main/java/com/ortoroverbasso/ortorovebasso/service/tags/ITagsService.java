package com.ortoroverbasso.ortorovebasso.service.tags;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.tags.TagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.TagsResponseDto;

public interface ITagsService {
    TagsResponseDto getTagById(Long id);

    List<TagsResponseDto> getAllTags();

    TagsResponseDto createTag(TagsRequestDto tagsRequestDto);

    TagsResponseDto updateTag(Long id, TagsRequestDto tagsRequestDto);

    void deleteTag(Long id);
}
