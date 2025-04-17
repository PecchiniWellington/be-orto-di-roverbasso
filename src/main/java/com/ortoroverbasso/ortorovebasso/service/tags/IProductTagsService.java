package com.ortoroverbasso.ortorovebasso.service.tags;

import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsResponseDto;

public interface IProductTagsService {
    ProductTagsResponseDto getProductTagsById(Long productId);

    ProductTagsResponseDto createProductTag(ProductTagsRequestDto productTagsRequestDto);

    ProductTagsResponseDto updateProductTag(Long productId, ProductTagsRequestDto productTagsRequestDto);

    void deleteProductTag(Long id);
}
