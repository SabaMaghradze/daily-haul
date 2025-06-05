package com.app.daily_haul.controller;

import com.app.daily_haul.dto.CartItemRequest;
import com.app.daily_haul.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addToCart(@RequestHeader("X-User-ID") String userId,
                                          @RequestBody CartItemRequest cartItemRequest) {

        cartService.addToCart(userId, cartItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
