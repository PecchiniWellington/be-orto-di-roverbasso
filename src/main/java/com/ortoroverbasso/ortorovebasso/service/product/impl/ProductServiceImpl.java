package com.ortoroverbasso.ortorovebasso.service.product.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesShortDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_informations.ProductInformationEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_information.ProductInformationMapper;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_attributes.ProductAttributesRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_information.ProductInformationRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_large_quantity_price.ProductLargeQuantityPriceRepository;
import com.ortoroverbasso.ortorovebasso.repository.tags.ProductTagsRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements IProductService {

        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private ProductInformationRepository productInformationRepository;
        @Autowired
        private ProductLargeQuantityPriceRepository productLargeQuantityPriceRepository;
        @Autowired
        private ProductTagsRepository productTagsRepository;
        @Autowired
        private ProductAttributesRepository productAttributesRepository;

        @Override
        public ProductResponseDto createProduct(ProductRequestDto dto) {

                ProductEntity product = ProductMapper.toEntity(dto);

                if (dto.getPriceLargeQuantities() == null) {
                        product.setPriceLargeQuantities(new ArrayList<>());
                } else {
                        List<Long> ids = dto.getPriceLargeQuantities().stream()
                                        .map(ProductLargeQuantityPriceRequestDto::getId)
                                        .collect(Collectors.toList());

                        List<ProductLargeQuantityPriceEntity> prices = productLargeQuantityPriceRepository
                                        .findAllById(ids);

                        if (prices.size() != ids.size()) {
                                throw new EntityNotFoundException("Some ProductLargeQuantityPriceEntity not found");
                        }

                        product.setPriceLargeQuantities(prices);
                }

                product = productRepository.save(product);

                return ProductMapper.toResponseDto(product);
        }

        @Override
        public List<ProductResponseDto> getAllProducts() {
                List<ProductEntity> products = productRepository.findAll();

                return products.stream().map(product -> {
                        ProductResponseDto productDto = ProductMapper.toResponseDto(product);

                        productDto.setTags(productTagsRepository.existsByProductId(product.getId()));
                        productDto.setAttributes(productAttributesRepository.existsByProductId(product.getId()));

                        List<ProductImagesShortDto> productImagesDtos = product.getProductImages().stream()
                                        .map(image -> new ProductImagesShortDto(
                                                        image.getId(),
                                                        image.getUrl(),
                                                        image.isCover()))
                                        .collect(Collectors.toList());
                        productDto.setProductImages(productImagesDtos);

                        return productDto;
                }).collect(Collectors.toList());
        }

        @Override
        public ProductResponseDto getProductById(Long productId) {
                ProductEntity product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

                boolean hasTags = productTagsRepository.existsByProductId(productId);
                boolean hasAttributes = productAttributesRepository.existsByProductId(productId);

                ProductResponseDto productDto = ProductMapper.toResponseDto(product);

                productDto.setTags(hasTags);
                productDto.setAttributes(hasAttributes);

                List<ProductImagesShortDto> productImagesDtos = product.getProductImages().stream()
                                .map(image -> new ProductImagesShortDto(
                                                image.getId(),
                                                image.getUrl(),
                                                image.isCover()))
                                .collect(Collectors.toList());
                productDto.setProductImages(productImagesDtos);

                ProductInformationEntity productInformation = productInformationRepository
                                .findByProductId(productId);

                ProductInformationResponseDto productInformationResponseDto = ProductInformationMapper
                                .toResponseDto(productInformation);

                productDto.setProductInformation(productInformationResponseDto);

                return productDto;
        }

        @Override
        public GenericResponseDto deleteProduct(Long productId) {
                ProductEntity product = productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId));

                productInformationRepository.deleteById(product.getId());

                productRepository.delete(product);

                return new GenericResponseDto(200, "Prodotto eliminato con successo. ID: " + productId);
        }

}
