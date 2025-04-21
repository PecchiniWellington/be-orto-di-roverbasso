package com.ortoroverbasso.ortorovebasso.mapper.taxonomy;

import com.ortoroverbasso.ortorovebasso.dto.taxonomy.TaxonomyResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.taxonomy.TaxonomyEntity;

public class TaxonomyMapper {

    public static TaxonomyResponseDto toDto(TaxonomyEntity entity) {
        TaxonomyResponseDto dto = new TaxonomyResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUrl(entity.getUrl());
        dto.setParentTaxonomy(entity.getParentTaxonomy());
        dto.setDateAdd(entity.getDateAdd().toString());
        dto.setDateUpd(entity.getDateUpd().toString());
        dto.setUrlImages(entity.getUrlImages());
        dto.setIsoCode(entity.getIsoCode());
        return dto;
    }
}
