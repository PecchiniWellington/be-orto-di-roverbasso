package com.ortoroverbasso.ortorovebasso.service.product.implementation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ortoroverbasso.ortorovebasso.constants.product.ProductConstants;
import com.ortoroverbasso.ortorovebasso.dto.GenericResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.paginate.PaginatedResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFacetResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.filters.product_filters.ProductFilterRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductFlatDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.entity.category.CategoryEntity;
import com.ortoroverbasso.ortorovebasso.entity.manufacturer.ManufacturerEntity;
import com.ortoroverbasso.ortorovebasso.entity.product.ProductEntity;
import com.ortoroverbasso.ortorovebasso.exception.CategoryNotFoundException;
import com.ortoroverbasso.ortorovebasso.exception.ManufacturerNotFoundException;
import com.ortoroverbasso.ortorovebasso.exception.ProductAlreadyExistsException;
import com.ortoroverbasso.ortorovebasso.exception.ProductNotFoundException;
import com.ortoroverbasso.ortorovebasso.mapper.product.ProductMapper;
import com.ortoroverbasso.ortorovebasso.repository.category.CategoryRepository;
import com.ortoroverbasso.ortorovebasso.repository.manufacturer.ManufacturerRepository;
import com.ortoroverbasso.ortorovebasso.repository.product.ProductRepository;
import com.ortoroverbasso.ortorovebasso.service.product.IProductService;
import com.ortoroverbasso.ortorovebasso.service.product.business.ProductBusinessLogic;
import com.ortoroverbasso.ortorovebasso.service.product.product_filters.IProductFacetService;
import com.ortoroverbasso.ortorovebasso.utils.pagination.PaginationUtils;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

        private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

        private final ProductRepository productRepository;
        private final CategoryRepository categoryRepository;
        private final ManufacturerRepository manufacturerRepository;
        private final IProductFacetService productFacetService;
        private final ProductBusinessLogic productBusinessLogic;
        private final ProductMapper productMapper;

        @Autowired
        public ProductServiceImpl(
                        ProductRepository productRepository,
                        CategoryRepository categoryRepository,
                        ManufacturerRepository manufacturerRepository,
                        IProductFacetService productFacetService,
                        ProductBusinessLogic productBusinessLogic,
                        ProductMapper productMapper) {
                this.productRepository = productRepository;
                this.categoryRepository = categoryRepository;
                this.manufacturerRepository = manufacturerRepository;
                this.productFacetService = productFacetService;
                this.productBusinessLogic = productBusinessLogic;
                this.productMapper = productMapper;
        }

        @Override
        @CacheEvict(value = { ProductConstants.CACHE_PRODUCTS,
                        ProductConstants.CACHE_FLAT_PRODUCTS }, allEntries = true)
        public ProductResponseDto createProduct(ProductRequestDto dto) {
                logger.info("Creating new product with SKU: {}", dto.getSku());

                // Verifica SKU univoco
                if (productRepository.existsBySku(dto.getSku())) {
                        throw new ProductAlreadyExistsException(
                                        String.format(ProductConstants.PRODUCT_ALREADY_EXISTS, dto.getSku()));
                }

                // Verifica categoria
                CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                                .orElseThrow(() -> new CategoryNotFoundException(
                                                ProductConstants.CATEGORY_NOT_FOUND + dto.getCategoryId()));

                // Verifica produttore (se specificato)
                ManufacturerEntity manufacturer = null;
                if (dto.getManufacturerId() != null) {
                        manufacturer = manufacturerRepository.findById(dto.getManufacturerId())
                                        .orElseThrow(() -> new ManufacturerNotFoundException(
                                                        ProductConstants.MANUFACTURER_NOT_FOUND
                                                                        + dto.getManufacturerId()));
                }

                // Crea entità usando mapper
                ProductEntity product = productMapper.toEntity(dto);
                product.setCategory(category);
                product.setManufacturer(manufacturer);

                // Validazione business logic
                productBusinessLogic.validateForCreate(product);

                // Salva prodotto
                ProductEntity savedProduct = productRepository.save(product);

                logger.info(ProductConstants.PRODUCT_CREATED + savedProduct.getId());
                return productMapper.toResponseDto(savedProduct);
        }

        @Override
        @Transactional(readOnly = true)
        @Cacheable(value = ProductConstants.CACHE_PRODUCTS, key = "#pageable.pageNumber + '_' + #pageable.pageSize")
        public PaginatedResponseDto<ProductResponseDto> getAllProducts(Pageable pageable) {
                logger.debug("Retrieving all products with pagination: page={}, size={}",
                                pageable.getPageNumber(), pageable.getPageSize());

                Page<ProductEntity> productsPage = productRepository.findAllWithDetails(pageable);
                List<ProductResponseDto> productDtos = productMapper.toResponseDtoList(productsPage.getContent());

                // Usa PaginationUtils
                return PaginationUtils.toPaginatedResponse(productDtos, pageable, productsPage.getTotalElements());
        }

        @Override
        @Transactional(readOnly = true)
        public ProductResponseDto getProductById(Long productId) {
                logger.debug("Retrieving product with ID: {}", productId);

                ProductEntity product = productRepository.findFullProductById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(
                                                ProductConstants.PRODUCT_NOT_FOUND + productId));

                return productMapper.toResponseDto(product);
        }

        @Override
        @CacheEvict(value = { ProductConstants.CACHE_PRODUCTS,
                        ProductConstants.CACHE_FLAT_PRODUCTS }, allEntries = true)
        public ProductResponseDto updateProduct(Long productId, ProductRequestDto dto) {
                logger.info("Updating product with ID: {}", productId);

                ProductEntity existingProduct = productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(
                                                ProductConstants.PRODUCT_NOT_FOUND + productId));

                // Verifica SKU univoco (se cambiato)
                if (!existingProduct.getSku().equals(dto.getSku()) && productRepository.existsBySku(dto.getSku())) {
                        throw new ProductAlreadyExistsException(
                                        String.format(ProductConstants.PRODUCT_ALREADY_EXISTS, dto.getSku()));
                }

                // Verifica categoria (se specificata)
                if (dto.getCategoryId() != null) {
                        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                                        .orElseThrow(() -> new CategoryNotFoundException(
                                                        ProductConstants.CATEGORY_NOT_FOUND + dto.getCategoryId()));
                        existingProduct.setCategory(category);
                }

                // Verifica produttore (se specificato)
                if (dto.getManufacturerId() != null) {
                        ManufacturerEntity manufacturer = manufacturerRepository.findById(dto.getManufacturerId())
                                        .orElseThrow(() -> new ManufacturerNotFoundException(
                                                        ProductConstants.MANUFACTURER_NOT_FOUND
                                                                        + dto.getManufacturerId()));
                        existingProduct.setManufacturer(manufacturer);
                }

                // Aggiorna campi usando mapper
                productMapper.updateEntityFromDto(dto, existingProduct);

                // Validazione business logic
                productBusinessLogic.validateForUpdate(existingProduct);

                ProductEntity savedProduct = productRepository.save(existingProduct);

                logger.info(ProductConstants.PRODUCT_UPDATED + savedProduct.getId());
                return productMapper.toResponseDto(savedProduct);
        }

        @Override
        @CacheEvict(value = { ProductConstants.CACHE_PRODUCTS,
                        ProductConstants.CACHE_FLAT_PRODUCTS }, allEntries = true)
        public GenericResponseDto deleteProduct(Long productId) {
                logger.info("Deleting product with ID: {}", productId);

                ProductEntity product = productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(
                                                ProductConstants.PRODUCT_NOT_FOUND + productId));

                productRepository.delete(product);

                logger.info(ProductConstants.PRODUCT_DELETED + productId);
                return new GenericResponseDto(200, ProductConstants.PRODUCT_DELETED + productId);
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
                logger.debug("Retrieving products for category ID: {}", categoryId);

                CategoryEntity category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new CategoryNotFoundException(
                                                ProductConstants.CATEGORY_NOT_FOUND + categoryId));

                List<ProductEntity> products = productRepository.findByCategory(category);
                return productMapper.toResponseDtoList(products);
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductResponseDto> getProductsByCategorySlug(String slug) {
                logger.debug("Retrieving products for category slug: {}", slug);

                CategoryEntity category = categoryRepository.findBySlugWithSubCategories(slug)
                                .orElseThrow(() -> new CategoryNotFoundException(
                                                "Category not found with slug: " + slug));

                // Raccogli tutti gli ID delle categorie e sottocategorie
                List<Long> categoryIds = new ArrayList<>();
                collectCategoryIdsRecursively(category, categoryIds);

                // Un'unica query per ottenere tutti i prodotti delle categorie
                List<ProductEntity> products = productRepository.findAllActiveByCategoryIdsWithDetails(categoryIds);

                return productMapper.toResponseDtoList(products);
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductResponseDto> getProductsBySubCategorySlug(String slug) {
                logger.debug("Retrieving products for subcategory slug: {}", slug);

                CategoryEntity subCategory = categoryRepository.findBySlug(slug)
                                .orElseThrow(() -> new CategoryNotFoundException(
                                                "Subcategory not found with slug: " + slug));

                List<ProductEntity> products = productRepository.findByCategoryId(subCategory.getId());
                return productMapper.toResponseDtoList(products);
        }

        @Override
        @Transactional(readOnly = true)
        public PaginatedResponseDto<ProductResponseDto> getFilteredProducts(
                        ProductFilterRequestDto filterDto, Pageable pageable) {

                Page<ProductEntity> productsPage = productRepository.findFilteredProducts(filterDto, pageable);
                List<ProductResponseDto> productDtos = productMapper.toResponseDtoList(productsPage.getContent());

                // Usa PaginationUtils
                return PaginationUtils.toPaginatedResponse(productDtos, pageable, productsPage.getTotalElements());
        }

        @Override
        @Transactional(readOnly = true)
        public ProductFacetResponseDto getAvailableFilters(ProductFilterRequestDto filterDto) {
                logger.debug("Retrieving available filters for criteria: {}", filterDto);
                return productFacetService.getFacets(filterDto);
        }

        @Override
        @Transactional(readOnly = true)
        @Cacheable(value = ProductConstants.CACHE_FLAT_PRODUCTS)
        public List<ProductFlatDto> getFlatProducts() {
                return productRepository.findAllFlat();
        }

        @Override
        @Transactional(readOnly = true)
        public Page<ProductFlatDto> getFlatProductsPaginated(Pageable pageable) {
                logger.debug("Retrieving paginated flat products: page={}, size={}",
                                pageable.getPageNumber(), pageable.getPageSize());

                return productRepository.findAllFlatPaginated(pageable);
        }

        @Override
        @Transactional(readOnly = true)
        public boolean existsById(Long productId) {
                return productRepository.existsById(productId);
        }

        @Override
        @Transactional(readOnly = true)
        public boolean existsBySku(String sku) {
                return productRepository.existsBySku(sku);
        }

        @Override
        @Transactional(readOnly = true)
        public long countActiveProducts() {
                return productRepository.countByActiveTrue();
        }

        @Override
        @Transactional(readOnly = true)
        public PaginatedResponseDto<ProductResponseDto> getProductsByPriceRange(
                        Double minPrice, Double maxPrice, Pageable pageable) {
                logger.debug("Retrieving products by price range: {} - {}", minPrice, maxPrice);

                Page<ProductEntity> productsPage = productRepository.findByRetailPriceBetween(
                                minPrice, maxPrice, pageable);

                List<ProductResponseDto> productDtos = productMapper.toResponseDtoList(productsPage.getContent());

                // Usa PaginationUtils
                return PaginationUtils.toPaginatedResponse(productDtos, pageable, productsPage.getTotalElements());
        }

        @Override
        @CacheEvict(value = { ProductConstants.CACHE_PRODUCTS,
                        ProductConstants.CACHE_FLAT_PRODUCTS }, allEntries = true)
        public ProductResponseDto updateProductStatus(Long productId, Boolean active) {
                logger.info("Updating product status for ID: {} to active: {}", productId, active);

                ProductEntity product = productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(
                                                ProductConstants.PRODUCT_NOT_FOUND + productId));

                product.setActive(active);
                ProductEntity savedProduct = productRepository.save(product);

                logger.info(ProductConstants.PRODUCT_STATUS_UPDATED + productId);
                return productMapper.toResponseDto(savedProduct);
        }

        @Override
        @CacheEvict(value = { ProductConstants.CACHE_PRODUCTS,
                        ProductConstants.CACHE_FLAT_PRODUCTS }, allEntries = true)
        public ProductResponseDto updateProductQuantity(Long productId, Integer quantity) {
                logger.info("Updating product quantity for ID: {} to quantity: {}", productId, quantity);

                ProductEntity product = productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(
                                                ProductConstants.PRODUCT_NOT_FOUND + productId));

                product.setQuantity(quantity);
                ProductEntity savedProduct = productRepository.save(product);

                logger.info(ProductConstants.PRODUCT_QUANTITY_UPDATED + productId);
                return productMapper.toResponseDto(savedProduct);
        }

        // Metodi helper privati

        private void collectCategoryIdsRecursively(CategoryEntity category, List<Long> ids) {
                ids.add(category.getId());

                if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
                        for (CategoryEntity sub : category.getSubCategories()) {
                                collectCategoryIdsRecursively(sub, ids);
                        }
                }
        }
}
