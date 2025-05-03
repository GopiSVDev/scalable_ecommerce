package com.gopiwebdev.ecommerce.product_service.service;

import com.gopiwebdev.ecommerce.product_service.dto.ProductDTO;
import com.gopiwebdev.ecommerce.product_service.dto.UpdateProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productRequestDto);

    ProductDTO updateProduct(Long id, UpdateProductDTO dto);

    ProductDTO getProductById(Long id);

    Page<ProductDTO> getProductsByCategory(String category, int page, int size);

    Page<ProductDTO> searchProductsByTitle(String title, int page, int size);

    List<ProductDTO> getAll();

    void deleteProduct(Long id);
}
