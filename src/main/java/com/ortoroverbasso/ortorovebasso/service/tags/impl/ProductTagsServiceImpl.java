package com.ortoroverbasso.ortorovebasso.service.tags.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.TagsRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.tags.ProductTagsEntity;
import com.ortoroverbasso.ortorovebasso.entity.tags.TagsEntity;
import com.ortoroverbasso.ortorovebasso.mapper.tags.ProductTagsMapper;
import com.ortoroverbasso.ortorovebasso.repository.tags.ProductTagsRepository;
import com.ortoroverbasso.ortorovebasso.service.tags.IProductTagsService;

@Service
public class ProductTagsServiceImpl implements IProductTagsService {

    @Autowired
    private ProductTagsRepository productTagsRepository;

    @Override
    public ProductTagsResponseDto getProductTagsById(Long id) {
        ProductTagsEntity productTagsEntity = productTagsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductTag not found"));
        return ProductTagsMapper.toResponse(productTagsEntity);
    }

    @Override
    public ProductTagsResponseDto createProductTag(ProductTagsRequestDto productTagsRequestDto) {
        ProductTagsEntity productTagsEntity = ProductTagsMapper.toEntity(productTagsRequestDto);
        productTagsEntity = productTagsRepository.save(productTagsEntity);
        return ProductTagsMapper.toResponse(productTagsEntity);
    }

    @Override
    public void deleteProductTag(Long id) {
        productTagsRepository.deleteById(id);
    }

    @Override
    public ProductTagsResponseDto updateProductTag(Long productId, ProductTagsRequestDto productTagsRequestDto) {
        ProductTagsEntity productTagsEntity = productTagsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("ProductTag not found"));

        TagsEntity tagEntity = productTagsEntity.getTag();

        if (productTagsRequestDto.getSku() != null) {
            productTagsEntity.setSku(productTagsRequestDto.getSku());
        }

        TagsRequestDto tagDto = productTagsRequestDto.getTag();

        if (tagDto != null) {
            if (tagDto.getId() != null) {
                tagEntity.setId(tagDto.getId());
            }
            if (tagDto.getName() != null) {
                tagEntity.setName(tagDto.getName());
            }
            if (tagDto.getLinkRewrite() != null) {
                tagEntity.setLinkRewrite(tagDto.getLinkRewrite());
            }
            if (tagDto.getLanguage() != null) {
                tagEntity.setLanguage(tagDto.getLanguage());
            }
        }

        productTagsRepository.save(productTagsEntity);

        return ProductTagsMapper.toResponse(productTagsEntity);
    }

}
