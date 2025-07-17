package com.ortoroverbasso.ortorovebasso.service.product.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.ortoroverbasso.ortorovebasso.service.product.product_filters.IProductFacetService;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

        private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

        private final ProductRepository productRepository;
        private final CategoryRepository categoryRepository;
        private final ManufacturerRepository manufacturerRepository;
        private final IProductFacetService productFacetService;

        @Autowired
        public ProductServiceImpl(
                        ProductRepository productRepository,
                        CategoryRepository categoryRepository,
                        ManufacturerRepository manufacturerRepository,
                        IProductFacetService productFacetService) {
                this.productRepository = productRepository;
                this.categoryRepository = categoryRepository;
                this.manufacturerRepository = manufacturerRepository;
                this.productFacetService = productFacetService;
        }

        @Override
        @CacheEvict(value = { "products", "flatProducts" }, allEntries = true)
        public ProductResponseDto createProduct(ProductRequestDto dto) {
                logger.info("Creating new product with SKU: {}", dto.getSku());

                // Verifica SKU univoco
                if (productRepository.existsBySku(dto.getSku())) {
                        throw new ProductAlreadyExistsException(
                                        "Product with SKU '" + dto.getSku() + "' already exists");
                }

                // Verifica categoria
                CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                                .orElseThrow(() -> new CategoryNotFoundException(
                                                "Category not found with ID: " + dto.getCategoryId()));

                // Verifica produttore (se specificato)
                ManufacturerEntity manufacturer = null;
                if (dto.getManufacturerId() != null) {
                        manufacturer = manufacturerRepository.findById(dto.getManufacturerId())
                                        .orElseThrow(() -> new ManufacturerNotFoundException(
                                                        "Manufacturer not found with ID: " + dto.getManufacturerId()));
                }

                // Crea entità
                ProductEntity product = ProductMapper.toEntity(dto);
                product.setCategory(category);
                product.setManufacturer(manufacturer);

                // Salva prodotto
                ProductEntity savedProduct = productRepository.save(product);

                logger.info("Product created successfully with ID: {}", savedProduct.getId());
                return ProductMapper.toResponseDto(savedProduct);
        }

        @Override
        @Transactional(readOnly = true)
        @Cacheable(value = "products", key = "#pageable.pageNumber + '_' + #pageable.pageSize")
        public PaginatedResponseDto<ProductResponseDto> getAllProducts(Pageable pageable) {
                logger.debug("Retrieving all products with pagination: page={}, size={}",
                                pageable.getPageNumber(), pageable.getPageSize());

                Page<ProductEntity> productsPage = productRepository.findAllWithDetails(pageable);

                List<ProductResponseDto> productDtos = ProductMapper.toResponseDtoList(productsPage.getContent());

                return PaginatedResponseDto.fromPage(
                                new PageImpl<>(productDtos, pageable, productsPage.getTotalElements()));
        }

        @Override
        @Transactional(readOnly = true)
        public ProductResponseDto getProductById(Long productId) {
                logger.debug("Retrieving product with ID: {}", productId);

                ProductEntity product = productRepository.findFullProductById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(
                                                "Product not found with ID: " + productId));

                return ProductMapper.toResponseDto(product);
        }

        @Override
        @CacheEvict(value = { "products", "flatProducts" }, allEntries = true)
        public ProductResponseDto updateProduct(Long productId, ProductRequestDto dto) {
                logger.info("Updating product with ID: {}", productId);

                ProductEntity existingProduct = productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(
                                                "Product not found with ID: " + productId));

                // Verifica SKU univoco (se cambiato)
                if (!existingProduct.getSku().equals(dto.getSku()) && productRepository.existsBySku(dto.getSku())) {
                        throw new ProductAlreadyExistsException(
                                        "Product with SKU '" + dto.getSku() + "' already exists");
                }

                // Verifica categoria (se specificata)
                if (dto.getCategoryId() != null) {
                        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                                        .orElseThrow(() -> new CategoryNotFoundException(
                                                        "Category not found with ID: " + dto.getCategoryId()));
                        existingProduct.setCategory(category);
                }

                // Verifica produttore (se specificato)
                if (dto.getManufacturerId() != null) {
                        ManufacturerEntity manufacturer = manufacturerRepository.findById(dto.getManufacturerId())
                                        .orElseThrow(() -> new ManufacturerNotFoundException(
                                                        "Manufacturer not found with ID: " + dto.getManufacturerId()));
                        existingProduct.setManufacturer(manufacturer);
                }

                // Aggiorna campi
                ProductMapper.updateEntityFromDto(dto, existingProduct);

                ProductEntity savedProduct = productRepository.save(existingProduct);

                logger.info("Product updated successfully with ID: {}", savedProduct.getId());
                return ProductMapper.toResponseDto(savedProduct);
        }

        @Override
        @CacheEvict(value = { "products", "flatProducts" }, allEntries = true)
        public GenericResponseDto deleteProduct(Long productId) {
                logger.info("Deleting product with ID: {}", productId);

                ProductEntity product = productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(
                                                "Product not found with ID: " + productId));

                productRepository.delete(product);

                logger.info("Product deleted successfully with ID: {}", productId);
                return new GenericResponseDto(200, "Product deleted successfully. ID: " + productId);
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
                logger.debug("Retrieving products for category ID: {}", categoryId);

                CategoryEntity category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new CategoryNotFoundException(
                                                "Category not found with ID: " + categoryId));

                List<ProductEntity> products = productRepository.findByCategory(category);
                return ProductMapper.toResponseDtoList(products);
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductResponseDto> getProductsByCategorySlug(String slug) {
                logger.debug("Retrieving products for category slug: {}", slug);

                CategoryEntity category = categoryRepository.findBySlugWithSubCategories(slug);
                if (category == null) {
                        throw new CategoryNotFoundException("Category not found with slug: " + slug);
                }

                return collectProductsFromCategoryTree(category);
        }

        @Override
        @Transactional(readOnly = true)
        public List<ProductResponseDto> getProductsBySubCategorySlug(String slug) {
                logger.debug("Retrieving products for subcategory slug: {}", slug);

                // Per sottocategorie, recuperiamo solo i prodotti di quella specifica categoria
                CategoryEntity subCategory = categoryRepository.findBySlug(slug);
                if (subCategory == null) {
                        throw new CategoryNotFoundException(
                                        "Subcategory not found with slug: " + slug);
                }

                List<ProductEntity> products = productRepository.findByCategoryId(subCategory.getId());
                return ProductMapper.toResponseDtoList(products);
        }

        @Override
        @Transactional(readOnly = true)
        public PaginatedResponseDto<ProductResponseDto> getFilteredProducts(
                        ProductFilterRequestDto filterDto, Pageable pageable) {
                logger.debug("Retrieving filtered products with criteria: {}", filterDto);

                return productFacetService.getFilteredProducts(filterDto, pageable);
        }

        @Override
        @Transactional(readOnly = true)
        public ProductFacetResponseDto getAvailableFilters(ProductFilterRequestDto filterDto) {
                logger.debug("Retrieving available filters for criteria: {}", filterDto);

                return productFacetService.getFacets(filterDto);
        }

        @Override
        @Transactional(readOnly = true)
        @Cacheable(value = "flatProducts")
        public List<ProductFlatDto> getFlatProducts() {

                List<ProductFlatDto> result = productRepository.findAllFlat();

                return result;
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

                List<ProductResponseDto> productDtos = ProductMapper.toResponseDtoList(productsPage.getContent());

                return PaginatedResponseDto.fromPage(
                                new PageImpl<>(productDtos, pageable, productsPage.getTotalElements()));
        }

        @Override
        @CacheEvict(value = { "products", "flatProducts" }, allEntries = true)
        public ProductResponseDto updateProductStatus(Long productId, Boolean active) {
                logger.info("Updating product status for ID: {} to active: {}", productId, active);

                ProductEntity product = productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(
                                                "Product not found with ID: " + productId));

                product.setActive(active);
                ProductEntity savedProduct = productRepository.save(product);

                logger.info("Product status updated successfully for ID: {}", productId);
                return ProductMapper.toResponseDto(savedProduct);
        }

        @Override
        @CacheEvict(value = { "products", "flatProducts" }, allEntries = true)
        public ProductResponseDto updateProductQuantity(Long productId, Integer quantity) {
                logger.info("Updating product quantity for ID: {} to quantity: {}", productId, quantity);

                ProductEntity product = productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(
                                                "Product not found with ID: " + productId));

                product.setQuantity(quantity);
                ProductEntity savedProduct = productRepository.save(product);

                logger.info("Product quantity updated successfully for ID: {}", productId);
                return ProductMapper.toResponseDto(savedProduct);
        }

        // Metodi helper privati

        private List<ProductResponseDto> collectProductsFromCategoryTree(CategoryEntity category) {
                List<ProductEntity> allProducts = new java.util.ArrayList<>();
                collectProductsRecursively(category, allProducts);
                return ProductMapper.toResponseDtoList(allProducts);
        }

        private void collectProductsRecursively(CategoryEntity category, List<ProductEntity> products) {
                // Aggiungi prodotti della categoria corrente
                List<ProductEntity> categoryProducts = productRepository.findByCategoryId(category.getId());
                products.addAll(categoryProducts);

                // Aggiungi prodotti delle sottocategorie
                if (category.getSubCategories() != null) {
                        for (CategoryEntity subCategory : category.getSubCategories()) {
                                collectProductsRecursively(subCategory, products);
                        }
                }
        }
}
