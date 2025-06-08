package com.app.daily_haul.controller;

import com.app.daily_haul.dto.CartItemRequest;
import com.app.daily_haul.dto.CartResponse;
import com.app.daily_haul.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                          @RequestBody CartItemRequest cartItemRequest) {

        if (!cartService.addToCart(userId, cartItemRequest)) {
            return ResponseEntity.badRequest().body("Product out of stock or user not found");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/remove-from-cart/{productId}")
    public ResponseEntity<String> removeFromCart(@RequestHeader("X-User-ID") String userId,
                                            @PathVariable Long productId) {

        boolean deleted = cartService.removeFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> fetchCart(@PathVariable Long cartId) {

        CartResponse response = cartService.fetchCart(cartId);

        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(response);
    }
    
    public ResponseEntity<List<CartResponse>> fetchUserCarts(@RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(cartService.fetchUserCarts(userId));
    }
}










