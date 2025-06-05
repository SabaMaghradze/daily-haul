package com.app.daily_haul.service.impl;

import com.app.daily_haul.dto.CartItemRequest;
import com.app.daily_haul.repository.CartRepository;
import com.app.daily_haul.repository.ProductRepository;
import com.app.daily_haul.repository.UserRepository;
import com.app.daily_haul.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public void addToCart(String userId, CartItemRequest cartItemRequest) {

    }
}
