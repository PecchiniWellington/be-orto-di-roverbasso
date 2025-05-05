package com.ortoroverbasso.ortorovebasso.service.product.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesShortDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_informations.ProductInformationEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.product_large_quantities_price.ProductLargeQuantityPriceEntity;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.mapper.product.product_information.ProductInformationMapper;
import com.ortoroverbasso.ortorovebasso.repository.category.CategoryRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_attributes.ProductAttributesRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_information.ProductInformationRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.product_large_quantity_price.ProductLargeQuantityPriceRepository;
import com.ortoroverbasso.ortorovebasso.repository.tags.ProductTagsRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;
import com.ortoroverbasso.ortorovebasso.utils.BeanUtilsHelper;

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
        @Autowired
        private CategoryRepository categoryRepository;

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
        public ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequest) {

                ProductEntity existingProduct = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

                ProductEntity updatedProduct = ProductMapper.toEntity(productRequest);

                updatedProduct.setId(productId);

                BeanUtils.copyProperties(
                                updatedProduct,
                                existingProduct,
                                BeanUtilsHelper.getNullPropertyNames(updatedProduct));

                if (updatedProduct.getPriceLargeQuantities() != null) {
                        existingProduct.setPriceLargeQuantities(updatedProduct.getPriceLargeQuantities());
                }
                if (updatedProduct.getProductInformation() != null) {
                        existingProduct.setProductInformation(updatedProduct.getProductInformation());
                }

                productRepository.save(existingProduct);

                return ProductMapper.toResponseDto(existingProduct);
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

                ProductInformationEntity productInformation = productInformationRepository.findByProductId(productId);

                if (productInformation != null) {
                        ProductInformationResponseDto productInformationResponseDto = ProductInformationMapper
                                        .toResponseDto(productInformation);
                        productDto.setProductInformation(productInformationResponseDto);
                } else {
                        productDto.setProductInformation(null);
                }

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

        public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
                // Trova la categoria dal suo ID
                CategoryEntity category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new RuntimeException("Category not found"));

                // Trova i prodotti per la categoria
                List<ProductEntity> products = productRepository.findByCategory(category);

                // Mappa i prodotti in DTO
                return products.stream()
                                .map(ProductMapper::toResponseDto)
                                .collect(Collectors.toList());
        }
}
