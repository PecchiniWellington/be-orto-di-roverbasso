package com.ortoroverbasso.ortorovebasso.service.tags.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.tags.TagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.TagsResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.tags.TagsEntity;
import com.ortoroverbasso.ortorovebasso.mapper.tags.TagsMapper;
import com.ortoroverbasso.ortorovebasso.repository.tags.TagsRepository;
import com.ortoroverbasso.ortorovebasso.service.tags.ITagsService;

@Service
public class TagsServiceImpl implements ITagsService {

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public TagsResponseDto getTagById(Long id) {
        TagsEntity tagsEntity = tagsRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        TagsResponseDto t = TagsMapper.toDto(tagsEntity);

        return t;
    }

    @Override
    public TagsResponseDto createTag(TagsRequestDto tagsDto) {
        TagsEntity tagsEntity = TagsMapper.toEntity(tagsDto);
        tagsEntity = tagsRepository.save(tagsEntity);
        return TagsMapper.toDto(tagsEntity);
    }

    @Override
    public TagsResponseDto updateTag(Long id, TagsRequestDto tagsDto) {
        TagsEntity tagsEntity = tagsRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        tagsEntity.setName(tagsDto.getName());
        tagsEntity.setLinkRewrite(tagsDto.getLinkRewrite());
        tagsEntity.setLanguage(tagsDto.getLanguage());
        tagsEntity = tagsRepository.save(tagsEntity);
        return TagsMapper.toDto(tagsEntity);
    }

    @Override
    public void deleteTag(Long id) {
        tagsRepository.deleteById(id);
    }

    @Override
    public List<TagsResponseDto> getAllTags() {
        List<TagsEntity> tagsEntities = tagsRepository.findAll();
        return tagsEntities.stream().map(TagsMapper::toDto).toList();
    }

}
