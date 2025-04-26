package com.gopiwebdev.ecommerce.product_service.controller;

import com.gopiwebdev.ecommerce.product_service.dto.ProductDTO;
import com.gopiwebdev.ecommerce.product_service.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        logger.info("Received request to create product: {}", dto);
        try {
            ProductDTO created = productService.createProduct(dto);
            logger.info("Product created successfully: {}", created);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating product: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        logger.info("Received request to get product by ID: {}", id);
        try {
            ProductDTO product = productService.getProductById(id);
            logger.info("Product retrieved successfully: {}", product);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            logger.error("Error retrieving product with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        logger.info("Received request to get products by category: {}, page: {}, size: {}", category, page, size);
        try {
            logger.info("Retrieved products for category {} successfully", category);
            Page<ProductDTO> products = productService.getProductsByCategory(category, page, size);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error retrieving products for category {}: {}", category, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProductsByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        logger.info("Received request to search products by title: '{}', page: {}, size: {}", title, page, size);
        try {
            Page<ProductDTO> products = productService.searchProductsByTitle(title, page, size);
            logger.info("Search results for title '{}' returned successfully", title);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error searching products by title '{}': {}", title, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
