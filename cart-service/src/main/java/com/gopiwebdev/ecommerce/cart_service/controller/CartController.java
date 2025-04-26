package com.gopiwebdev.ecommerce.cart_service.controller;

import com.gopiwebdev.ecommerce.cart_service.dto.CartItemResponse;
import com.gopiwebdev.ecommerce.cart_service.entity.CartItem;
import com.gopiwebdev.ecommerce.cart_service.exception.CartItemNotFoundException;
import com.gopiwebdev.ecommerce.cart_service.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        logger.info("Received request to add item to cart. UserId: {}, ProductId: {}, Quantity: {}", userId, productId, quantity);

        try {
            CartItem item = cartService.addItemToCart(userId, productId, quantity);
            logger.info("Item added to cart successfully: {}", item);
            return new ResponseEntity<>(item, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding item to cart. UserId: {}, ProductId: {}, Quantity: {}. Error: {}", userId, productId, quantity, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@PathVariable Long userId) {
        logger.info("Received request to get cart items for userId: {}", userId);

        try {
            List<CartItemResponse> items = cartService.getCartItems(userId);
            logger.info("Retrieved cart items for userId {}: {}", userId, items);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("Error retrieving cart items for userId {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/remove")
    public void removeItemFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        logger.info("Received request to remove item from cart. UserId: {}, ProductId: {}", userId, productId);

        try {
            cartService.removeItemFromCart(userId, productId);
            logger.info("Item removed from cart successfully. UserId: {}, ProductId: {}", userId, productId);
        } catch (Exception e) {
            logger.error("Error removing item from cart. UserId: {}, ProductId: {}. Error: {}", userId, productId, e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateItemQuantity(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        logger.info("Received request to update cart item quantity. UserId: {}, ProductId: {}, Quantity: {}", userId, productId, quantity);
        try {
            CartItem updatedItem = cartService.updateItemQuantity(userId, productId, quantity);
            if (updatedItem != null) {
                logger.info("Cart item updated successfully. UserId: {}, ProductId: {}, Quantity: {}", userId, productId, quantity);
                return ResponseEntity.ok(updatedItem);
            } else {
                logger.warn("Cart item not found for userId: {} and productId: {}", userId, productId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cart item not found for user ID " + userId + " and product ID " + productId);
            }
        } catch (CartItemNotFoundException e) {
            logger.error("Cart item not found for userId: {} and productId: {}. Error: {}", userId, productId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating cart item quantity. UserId: {}, ProductId: {}, Quantity: {}. Error: {}", userId, productId, quantity, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable Long userId) {
        logger.info("Received request to clear cart for userId: {}", userId);
        try {
            cartService.clearCart(userId);
            logger.info("Cart cleared successfully for userId: {}", userId);
        } catch (Exception e) {
            logger.error("Error clearing cart for userId {}. Error: {}", userId, e.getMessage());
        }
    }
}
