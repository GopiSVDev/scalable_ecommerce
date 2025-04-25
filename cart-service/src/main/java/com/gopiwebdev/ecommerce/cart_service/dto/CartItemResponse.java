package com.gopiwebdev.ecommerce.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemResponse {
    private Long id;
    private int quantity;
    private CartProductDTO product;

    public CartItemResponse(Long id, int quantity, CartProductDTO product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }
}
