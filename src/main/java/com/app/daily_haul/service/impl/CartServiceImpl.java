package com.app.daily_haul.service.impl;

import com.app.daily_haul.dto.CartItemRequest;
import com.app.daily_haul.dto.CartResponse;
import com.app.daily_haul.model.CartItem;
import com.app.daily_haul.model.Product;
import com.app.daily_haul.model.User;
import com.app.daily_haul.repository.CartRepository;
import com.app.daily_haul.repository.ProductRepository;
import com.app.daily_haul.repository.UserRepository;
import com.app.daily_haul.service.CartService;
import com.app.daily_haul.utils.Mappers;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {

        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
        if (productOpt.isEmpty()) {
            return false;
        }

        Product product = productOpt.get();

        if (product.getQuantity() < cartItemRequest.getQuantity()) {
            return false;
        }

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        CartItem existingCartItem = cartRepository.findByUserAndProduct(user, product);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem(user, product, cartItemRequest.getQuantity(),
                    product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));

            cartRepository.save(cartItem);
        }
        return true;

    }

    @Override
    public boolean removeFromCart(String userId, Long productId) {

        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (productOpt.isPresent() && userOpt.isPresent()) {
            cartRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }

        return false;
    }

    @Override
    public CartResponse fetchCart(Long cartId) {
        return cartRepository.findById(cartId)
                .map(Mappers::getCartResponse)
                .orElse(null);
    }
}
