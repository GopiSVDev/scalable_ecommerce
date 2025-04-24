package com.gopiwebdev.ecommerce.product_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String category;
    private String thumbnail;
    private Double rating;
    private Double discountPercentage;
    private List<String> images;

    public Product(String title, BigDecimal price, double discountPercentage, String category, String description, double rating, String thumbnail, List<String> images) {
        this.title = title;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.category = category;
        this.description = description;
        this.rating = rating;
        this.thumbnail = thumbnail;
        this.images = images;
    }
}
