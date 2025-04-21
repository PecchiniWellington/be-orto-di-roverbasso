package com.ortoroverbasso.ortorovebasso.service.taxonomy;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.taxonomy.TaxonomyRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.taxonomy.TaxonomyResponseDto;

public interface ITaxonomyService {
    List<TaxonomyResponseDto> getAllTaxonomies();

    TaxonomyResponseDto getTaxonomyById(Long id);

    TaxonomyResponseDto createTaxonomy(TaxonomyRequestDto dto);
}
