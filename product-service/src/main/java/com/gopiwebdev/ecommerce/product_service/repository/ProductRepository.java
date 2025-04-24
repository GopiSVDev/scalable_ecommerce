package com.gopiwebdev.ecommerce.product_service.repository;

import com.gopiwebdev.ecommerce.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(String category, Pageable pageable);

    Page<Product> findByTitleContaining(String title, Pageable pageable);
}
