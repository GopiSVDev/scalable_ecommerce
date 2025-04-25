package com.gopiwebdev.ecommerce.cart_service.controller;

import com.gopiwebdev.ecommerce.cart_service.dto.CartItemResponse;
import com.gopiwebdev.ecommerce.cart_service.entity.CartItem;
import com.gopiwebdev.ecommerce.cart_service.exception.CartItemNotFoundException;
import com.gopiwebdev.ecommerce.cart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        CartItem item = cartService.addItemToCart(userId, productId, quantity);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@PathVariable Long userId) {
        List<CartItemResponse> items = cartService.getCartItems(userId);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/remove")
    public void removeItemFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        cartService.removeItemFromCart(userId, productId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateItemQuantity(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        try {
            CartItem updatedItem = cartService.updateItemQuantity(userId, productId, quantity);
            if (updatedItem != null) {
                return ResponseEntity.ok(updatedItem);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cart item not found for user ID " + userId + " and product ID " + productId);
            }
        } catch (CartItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }
}
