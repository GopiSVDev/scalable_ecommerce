package com.gopiwebdev.ecommerce.cart_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartProductDTO {
    private String title;
    private BigDecimal price;
    private Double discountPercentage;
    private String thumbnail;

    public CartProductDTO(String title, BigDecimal price, Double discountPercentage, String thumbnail) {
        this.title = title;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.thumbnail = thumbnail;
    }
}
