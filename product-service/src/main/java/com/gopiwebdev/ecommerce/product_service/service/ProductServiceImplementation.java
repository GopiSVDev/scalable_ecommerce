package com.gopiwebdev.ecommerce.product_service.service;

import com.gopiwebdev.ecommerce.product_service.dto.ProductDTO;
import com.gopiwebdev.ecommerce.product_service.entity.Product;
import com.gopiwebdev.ecommerce.product_service.exception.ProductNotFoundException;
import com.gopiwebdev.ecommerce.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProductDTO> getProductsByCategory(String category) {
        List<Product> products = repository.findByCategory(category);

        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::new)
                .toList();

        return productDTOs;
    }

    @Override
    public List<ProductDTO> searchProductsByTitle(String title) {
        List<Product> products = repository.findByTitleContaining(title);
        return products.stream().map(ProductDTO::new).toList();
    }
}
