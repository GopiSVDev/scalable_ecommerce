package com.gopiwebdev.ecommerce.product_service.service;

import com.gopiwebdev.ecommerce.product_service.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productRequestDto);

    ProductDTO getProductById(Long id);

    List<ProductDTO> getProductsByCategory(String category);

    List<ProductDTO> searchProductsByTitle(String title);
}
