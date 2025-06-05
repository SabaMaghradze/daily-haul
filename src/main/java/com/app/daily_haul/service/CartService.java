package com.app.daily_haul.service;

import com.app.daily_haul.dto.CartItemRequest;

public interface CartService {
    void addToCart(String userId, CartItemRequest cartItemRequest);
}
