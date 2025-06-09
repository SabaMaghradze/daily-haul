package com.app.daily_haul.service.impl;

import com.app.daily_haul.dto.OrderResponse;
import com.app.daily_haul.model.*;
import com.app.daily_haul.repository.OrderRepository;
import com.app.daily_haul.repository.UserRepository;
import com.app.daily_haul.service.CartService;
import com.app.daily_haul.service.OrderService;
import com.app.daily_haul.utils.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;

    @Override
    public Optional<OrderResponse> createOrder(String userId) {

        List<CartItem> cartItems = cartService.fetchUserCarts(userId);

        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                )).toList();

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);

        return Optional.of(Mappers.getOrderResponse(order));

    }

    @Override
    public List<OrderResponse> getAllOrders(String userId) {
        return orderRepository.findByUser(userRepository.findById(Long.valueOf(userId)).orElse(null))
                .stream()
                .map(Mappers::getOrderResponse)
                .toList();
    }
}





