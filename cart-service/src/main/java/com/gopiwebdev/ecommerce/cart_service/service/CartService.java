package com.gopiwebdev.ecommerce.cart_service.service;

import com.gopiwebdev.ecommerce.cart_service.client.ProductClient;
import com.gopiwebdev.ecommerce.cart_service.dto.CartItemResponse;
import com.gopiwebdev.ecommerce.cart_service.dto.CartProductDTO;
import com.gopiwebdev.ecommerce.cart_service.entity.CartItem;
import com.gopiwebdev.ecommerce.cart_service.entity.ProductDTO;
import com.gopiwebdev.ecommerce.cart_service.exception.CartItemNotFoundException;
import com.gopiwebdev.ecommerce.cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartRepository repository;

    public CartItem addItemToCart(Long userId, Long productId, int quantity) {
        CartItem existingItem = repository.findByUserIdAndProductId(userId, productId);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return repository.save(existingItem);
        } else {
            CartItem newCartItem = new CartItem(userId, productId, quantity);
            return repository.save(newCartItem);
        }
    }

    public List<CartItemResponse> getCartItems(Long userId) {
        List<CartItem> cartItems = repository.findByUserId(userId);
        return cartItems.stream().map(cartItem -> {
            ProductDTO product = productClient.getProductById(cartItem.getProductId());
            CartProductDTO productDTO = new CartProductDTO(
                    product.getTitle(),
                    product.getPrice(),
                    product.getDiscountPercentage(),
                    product.getThumbnail()
            );
            return new CartItemResponse(cartItem.getId(), cartItem.getQuantity(), productDTO);
        }).collect(Collectors.toList());
    }

    public void removeItemFromCart(Long userId, Long productId) {
        CartItem cartItem = repository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            repository.delete(cartItem);
        }
    }

    public CartItem updateItemQuantity(Long userId, Long productId, int quantity) {
        CartItem cartItem = repository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            return repository.save(cartItem);
        } else
            throw new CartItemNotFoundException("Cart item with product ID " + productId + " for user ID " + userId + " not found.");
    }

    public void clearCart(Long userId) {
        List<CartItem> cartItems = repository.findByUserId(userId);
        repository.deleteAll(cartItems);
    }
}
