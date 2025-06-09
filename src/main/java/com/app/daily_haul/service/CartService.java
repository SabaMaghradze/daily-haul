package com.app.daily_haul.service;

import com.app.daily_haul.dto.CartItemRequest;
import com.app.daily_haul.dto.CartResponse;
import com.app.daily_haul.model.CartItem;

import java.util.List;

public interface CartService {
    boolean addToCart(String userId, CartItemRequest cartItemRequest);

    boolean removeFromCart(String userId, Long productId);

    CartResponse fetchCart(Long cartId);

    List<CartItem> fetchUserCarts(String userId);

    void clearCart(String userId);
}
