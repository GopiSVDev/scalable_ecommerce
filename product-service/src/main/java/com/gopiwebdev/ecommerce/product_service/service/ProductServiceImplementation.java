package com.gopiwebdev.ecommerce.product_service.service;

import com.gopiwebdev.ecommerce.product_service.dto.ProductDTO;
import com.gopiwebdev.ecommerce.product_service.dto.UpdateProductDTO;
import com.gopiwebdev.ecommerce.product_service.entity.Product;
import com.gopiwebdev.ecommerce.product_service.exception.ProductNotFoundException;
import com.gopiwebdev.ecommerce.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    ProductRepository repository;

    @Override
    public ProductDTO createProduct(ProductDTO productRequestDto) {
        Product product = new Product(
                productRequestDto.getTitle(),
                productRequestDto.getPrice(),
                productRequestDto.getDiscountPercentage(),
                productRequestDto.getCategory(),
                productRequestDto.getDescription(),
                productRequestDto.getRating(),
                productRequestDto.getThumbnail(),
                productRequestDto.getImages()
        );

        Product savedProduct = repository.save(product);

        return new ProductDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, UpdateProductDTO dto) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (dto.getTitle() != null) product.setTitle(dto.getTitle());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getQuantity() != null) product.setQuantity(dto.getQuantity());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());
        if (dto.getThumbnail() != null) product.setThumbnail(dto.getThumbnail());
        if (dto.getRating() != null) product.setRating(dto.getRating());
        if (dto.getDiscountPercentage() != null) product.setDiscountPercentage(dto.getDiscountPercentage());
        if (dto.getImages() != null) product.setImages(dto.getImages());

        return new ProductDTO(repository.save(product));
    }

    @Override
    @Cacheable("products")
    public ProductDTO getProductById(Long id) {
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return new ProductDTO(product);
        } else {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
    }

    @Override
    @Cacheable("products")
    public Page<ProductDTO> getProductsByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = repository.findByCategory(category, pageable);

        return productPage.map(ProductDTO::new);
    }

    @Override
    @Cacheable("products")
    public Page<ProductDTO> searchProductsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = repository.findByTitleContaining(title, pageable);
        return productPage.map(ProductDTO::new);
    }

    @Cacheable("products")
    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = repository.findAll();

        return products.stream()
                .map(ProductDTO::new)
                .toList();
    }

}
