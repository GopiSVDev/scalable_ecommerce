package com.gopiwebdev.ecommerce.product_service.controller;

import com.gopiwebdev.ecommerce.product_service.dto.ProductDTO;
import com.gopiwebdev.ecommerce.product_service.service.ProductService;
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

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        ProductDTO created = productService.createProduct(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.getProductsByCategory(category, page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProductsByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.searchProductsByTitle(title, page, size));
    }
}
