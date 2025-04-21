package com.ortoroverbasso.ortorovebasso.service.taxonomy.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.taxonomy.TaxonomyRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.taxonomy.TaxonomyResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.taxonomy.TaxonomyEntity;
import com.ortoroverbasso.ortorovebasso.mapper.taxonomy.TaxonomyMapper;
import com.ortoroverbasso.ortorovebasso.repository.taxonomy.TaxonomyRepository;
import com.ortoroverbasso.ortorovebasso.service.taxonomy.ITaxonomyService;

@Service
public class TaxonomyService implements ITaxonomyService {

    @Autowired
    private TaxonomyRepository taxonomyRepository;

    @Override
    public List<TaxonomyResponseDto> getAllTaxonomies() {
        return taxonomyRepository.findAll().stream()
                .map(TaxonomyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaxonomyResponseDto getTaxonomyById(Long id) {
        return taxonomyRepository.findById(id)
                .map(TaxonomyMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Taxonomy not found"));
    }

    @Override
    public TaxonomyResponseDto createTaxonomy(TaxonomyRequestDto dto) {
        TaxonomyEntity entity = new TaxonomyEntity();
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());
        entity.setParentTaxonomy(dto.getParentTaxonomy());
        entity.setUrlImages(dto.getUrlImages());
        entity.setIsoCode(dto.getIsoCode());

        Date currentDate = new Date();
        entity.setDateAdd(currentDate);
        entity.setDateUpd(currentDate);

        TaxonomyEntity savedEntity = taxonomyRepository.save(entity);

        return TaxonomyMapper.toDto(savedEntity);
    }
}
