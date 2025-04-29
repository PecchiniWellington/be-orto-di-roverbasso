package com.ortoroverbasso.ortorovebasso.service.tags.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.tags.ProductTagsResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.tags.ProductTagsEntity;
import com.ortoroverbasso.ortorovebasso.entity.tags.TagsEntity;
import com.ortoroverbasso.ortorovebasso.mapper.tags.ProductTagsMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.tags.ProductTagsRepository;
import com.ortoroverbasso.ortorovebasso.repository.tags.TagsRepository;
import com.ortoroverbasso.ortorovebasso.service.tags.IProductTagsService;

@Service
public class ProductTagsServiceImpl implements IProductTagsService {

    @Autowired
    private ProductTagsRepository productTagsRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public ProductTagsResponseDto getProductTagsById(Long id) {
        ProductTagsEntity productTagsEntity = productTagsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductTag not found"));
        return ProductTagsMapper.toResponseDto(productTagsEntity);
    }

    @Override
    public ProductTagsResponseDto createProductTag(ProductTagsRequestDto productTagsRequestDto) {
        ProductEntity product = productRepository.findById(productTagsRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<ProductTagsEntity> productTagsEntities = new ArrayList<>();

        for (Long tagId : productTagsRequestDto.getTagIds()) {
            TagsEntity tag = tagsRepository.findById(tagId)
                    .orElseThrow(() -> new RuntimeException("Tag not found"));

            ProductTagsEntity productTagsEntity = ProductTagsMapper.toEntity(productTagsRequestDto, product, tag);
            productTagsEntities.add(productTagsEntity);
        }

        productTagsRepository.saveAll(productTagsEntities);

        return ProductTagsMapper.toResponseDto(productTagsEntities.get(0)); // restituisce il primo elemento per esempio
    }

    @Override
    public void deleteProductTag(Long id) {
        productTagsRepository.deleteById(id);
    }

}
