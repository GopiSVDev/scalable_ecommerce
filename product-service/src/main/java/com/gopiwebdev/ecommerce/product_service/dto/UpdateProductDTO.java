package com.gopiwebdev.ecommerce.product_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateProductDTO {
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
