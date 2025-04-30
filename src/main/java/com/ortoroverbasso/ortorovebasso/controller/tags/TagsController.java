package com.ortoroverbasso.ortorovebasso.controller.tags;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.tags.TagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.TagsResponseDto;
import com.ortoroverbasso.ortorovebasso.service.tags.ITagsService;

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    @Autowired
    private ITagsService tagsService;

    @GetMapping("/all")
    public List<TagsResponseDto> getAllTags() {
        return tagsService.getAllTags();
    }

    @GetMapping("/{id}")
    public TagsResponseDto getTagById(@PathVariable Long id) {
        return tagsService.getTagById(id);
    }

    @PostMapping()
    public TagsResponseDto createTag(@RequestBody TagsRequestDto tagsRequestDto) {
        return tagsService.createTag(tagsRequestDto);
    }

    @PutMapping("/{id}")
    public TagsResponseDto updateTag(@PathVariable Long id, @RequestBody TagsRequestDto tagsRequestDto) {
        return tagsService.updateTag(id, tagsRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagsService.deleteTag(id);
    }

}
