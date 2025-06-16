package com.ortoroverbasso.ortorovebasso.service.product.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.paginate.PaginatedResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFacetResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductFlatDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_images.ProductImagesShortDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_information.ProductInformationResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.product_large_quantity_price.ProductLargeQuantityPriceResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
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
import com.ortoroverbasso.ortorovebasso.service.product.product_filters.IProductFacetService;
import com.ortoroverbasso.ortorovebasso.utils.BeanUtilsHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

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

        @Autowired
        private IProductFacetService productFacetService;

        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public ProductResponseDto createProduct(ProductRequestDto dto) {

                CategoryEntity category = categoryRepository.findById(dto.getCategory())
                                .orElseThrow(() -> new RuntimeException("Category not found"));

                ProductEntity product = ProductMapper.toEntity(dto);
                product.setCategory(category);

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

        @Transactional(readOnly = true)
        @Override
        public PaginatedResponseDto<ProductResponseDto> getAllProducts(Pageable pageable) {
                Page<ProductEntity> productsPage = productRepository.findAllWithDetails(pageable);

                List<ProductResponseDto> productDtos = productsPage.stream()
                                .map(product -> {
                                        product.getPriceLargeQuantities().size();

                                        ProductResponseDto dto = ProductMapper.toResponseDto(product);
                                        dto.setTags(productTagsRepository.existsByProductId(product.getId()));
                                        dto.setAttributes(
                                                        productAttributesRepository.existsByProductId(product.getId()));

                                        List<ProductImagesShortDto> imageDtos = product.getProductImages().stream()
                                                        .map(img -> new ProductImagesShortDto(img.getId(), img.getUrl(),
                                                                        img.isCover()))
                                                        .collect(Collectors.toList());
                                        dto.setProductImages(imageDtos);

                                        if (product.getProductInformation() != null) {
                                                dto.setProductInformation(ProductInformationMapper.toResponseDto(
                                                                product.getProductInformation()));
                                        }

                                        if (product.getCategory() != null) {
                                                dto.setCategoryId(product.getCategory().getId());
                                        }

                                        return dto;
                                })
                                .collect(Collectors.toList());

                return PaginatedResponseDto
                                .fromPage(new PageImpl<>(productDtos, pageable, productsPage.getTotalElements()));
        }

        @Override
        public ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequest) {
                ProductEntity existingProduct = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

                CategoryEntity category = categoryRepository.findById(productRequest.getCategory())
                                .orElseThrow(() -> new RuntimeException("Category not found"));

                ProductEntity updatedProduct = ProductMapper.toEntity(productRequest);
                updatedProduct.setId(productId);
                updatedProduct.setCategory(category);

                BeanUtils.copyProperties(updatedProduct, existingProduct,
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

        @Transactional(readOnly = true)
        @Override
        public ProductResponseDto getProductById(Long productId) {
                ProductEntity product = productRepository.findFullProductById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

                boolean hasTags = productTagsRepository.existsByProductId(productId);
                boolean hasAttributes = productAttributesRepository.existsByProductId(productId);

                ProductResponseDto productDto = ProductMapper.toResponseDto(product);

                if (product.getCategory() != null) {
                        productDto.setCategoryId(product.getCategory().getId());
                }

                productDto.setTags(hasTags);
                productDto.setAttributes(hasAttributes);

                List<ProductImagesShortDto> productImagesDtos = product.getProductImages().stream()
                                .map(image -> new ProductImagesShortDto(
                                                image.getId(),
                                                image.getUrl(),
                                                image.isCover()))
                                .collect(Collectors.toList());
                productDto.setProductImages(productImagesDtos);

                if (product.getProductInformation() != null) {
                        ProductInformationResponseDto productInformationResponseDto = ProductInformationMapper
                                        .toResponseDto(product.getProductInformation());
                        productDto.setProductInformation(productInformationResponseDto);
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

        @Override
        public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
                CategoryEntity category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new RuntimeException("Category not found"));

                List<ProductEntity> products = productRepository.findByCategory(category);

                return products.stream()
                                .map(product -> {
                                        product.getPriceLargeQuantities().size();
                                        return ProductMapper.toResponseDto(product);
                                })
                                .collect(Collectors.toList());
        }

        private void forceInit(CategoryEntity category) {
                if (category.getSubCategories() != null) {
                        category.getSubCategories().forEach(this::forceInit);
                }
        }

        @Transactional
        @Override
        public List<ProductResponseDto> getProductsByCategorySlug(String slug) {
                CategoryEntity category = categoryRepository.findBySlugWithSubCategories(slug);

                forceInit(category);

                List<ProductResponseDto> products = new ArrayList<>();
                collectProducts(category, products);
                return products;
        }

        private void collectProducts(CategoryEntity category, List<ProductResponseDto> products) {
                List<ProductEntity> productEntities = productRepository.findByCategoryId(category.getId());

                products.addAll(productEntities.stream()
                                .map(product -> {
                                        product.getPriceLargeQuantities().size();
                                        return ProductMapper.toResponseDto(product);
                                })
                                .collect(Collectors.toList()));

                for (CategoryEntity subCategory : category.getSubCategories()) {
                        collectProducts(subCategory, products);
                }
        }

        @Override
        public ProductFacetResponseDto getAvailableFilters(ProductFilterRequestDto filterDto) {
                return productFacetService.getFacets(filterDto);
        }

        @Transactional(readOnly = true)
        @Override
        public PaginatedResponseDto<ProductResponseDto> getFilteredProducts(ProductFilterRequestDto filterDto,
                        Pageable pageable) {

                PaginatedResponseDto<ProductResponseDto> paginated = productFacetService.getFilteredProducts(filterDto,
                                pageable);

                paginated.getItems().forEach(dto -> {
                        ProductEntity entity = productRepository.findById(dto.getId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Product not found during enrichment: " + dto.getId()));

                        // Forza l'inizializzazione
                        List<ProductLargeQuantityPriceEntity> prices = entity.getPriceLargeQuantities();
                        prices.size();

                        List<ProductLargeQuantityPriceResponseDto> priceDtos = prices.stream()
                                        .map(ProductMapper::toLargeQuantityDto)
                                        .collect(Collectors.toList());

                        dto.setPriceLargeQuantities(priceDtos);
                });

                return paginated;
        }

        @Transactional(readOnly = true)
        @Override
        public List<ProductFlatDto> getFlatProducts() {
                return productRepository.findAllFlat();
        }
}
