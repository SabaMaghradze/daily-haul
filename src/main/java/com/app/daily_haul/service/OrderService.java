package com.app.daily_haul.service;

import com.app.daily_haul.dto.OrderResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderResponse> createOrder(String userId);

    List<OrderResponse> getAllOrders(String userId);
}
