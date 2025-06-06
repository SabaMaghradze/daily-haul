package com.app.daily_haul.service;

import com.app.daily_haul.dto.CartItemRequest;
import com.app.daily_haul.dto.CartResponse;

public interface CartService {
    boolean addToCart(String userId, CartItemRequest cartItemRequest);

    boolean removeFromCart(String userId, Long productId);

    CartResponse fetchCart(Long cartId);
}
