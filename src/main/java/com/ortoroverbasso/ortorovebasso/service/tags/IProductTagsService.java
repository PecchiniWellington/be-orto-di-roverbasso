package com.ortoroverbasso.ortorovebasso.service.tags;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsResponseDto;

public interface IProductTagsService {
    ProductTagsResponseDto getProductTagsById(Long productId);

    ProductTagsResponseDto createProductTag(ProductTagsRequestDto productTagsRequestDto);

    void associateTagsToProduct(Long productId, List<Long> tagIds);

    void disassociateTagsFromProduct(Long productId, List<Long> tagIds);

    void deleteProductTag(Long id);
}
