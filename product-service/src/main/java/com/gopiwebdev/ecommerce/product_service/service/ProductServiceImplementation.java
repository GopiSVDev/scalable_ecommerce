package com.gopiwebdev.ecommerce.product_service.service;

import com.gopiwebdev.ecommerce.product_service.dto.ProductDTO;
import com.gopiwebdev.ecommerce.product_service.entity.Product;
import com.gopiwebdev.ecommerce.product_service.exception.ProductNotFoundException;
import com.gopiwebdev.ecommerce.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    ProductRepository repository;

    @Override
    public ProductDTO createProduct(@RequestBody ProductDTO productRequestDto) {
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
    public Page<ProductDTO> getProductsByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = repository.findByCategory(category, pageable);

        return productPage.map(ProductDTO::new);
    }

    @Override
    public Page<ProductDTO> searchProductsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = repository.findByTitleContaining(title, pageable);
        return productPage.map(ProductDTO::new);
    }
}
