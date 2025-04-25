package com.gopiwebdev.ecommerce.cart_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
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
}
