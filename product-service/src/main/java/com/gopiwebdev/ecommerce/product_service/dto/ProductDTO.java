package com.gopiwebdev.ecommerce.product_service.dto;

import com.gopiwebdev.ecommerce.product_service.entity.Product;
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

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.discountPercentage = product.getDiscountPercentage();
        this.category = product.getCategory();
        this.description = product.getDescription();
        this.rating = product.getRating();
        this.thumbnail = product.getThumbnail();
        this.images = product.getImages();
    }
}
