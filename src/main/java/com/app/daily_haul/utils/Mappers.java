package com.app.daily_haul.utils;

import com.app.daily_haul.dto.*;
import com.app.daily_haul.model.*;

import java.math.BigDecimal;
import java.util.List;

public class Mappers {

    public static ProductResponse getProductResponse(Product product) {
        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getImageUrl(),
                product.getActive());
    }

    public static Product productReqToEnt(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName() != null ? productRequest.getName() : product.getName());
        product.setDescription(productRequest.getDescription() != null ? productRequest.getDescription() : product.getDescription());
        product.setPrice(productRequest.getPrice() != null ? productRequest.getPrice() : product.getPrice());
        product.setQuantity(productRequest.getQuantity() != null ? productRequest.getQuantity() : product.getQuantity());
        product.setCategory(productRequest.getCategory() != null ? productRequest.getCategory() : product.getCategory());
        product.setImageUrl(productRequest.getImageUrl() != null ? productRequest.getImageUrl() : product.getImageUrl());

        return product;
    }

    public static UserResponse getUserResponse(User user) {
        UserResponse userResponse = new UserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO(user.getAddress().getStreet(),
                    user.getAddress().getCity(),
                    user.getAddress().getState(),
                    user.getAddress().getCountry(),
                    user.getAddress().getZipcode());

            userResponse.setAddress(addressDTO);
        }

        return userResponse;
    }

    public static User userReqToEnt(User user, UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName() != null ? userRequest.getFirstName() : user.getFirstName());
        user.setLastName(userRequest.getLastName() != null ? userRequest.getLastName() : user.getLastName());
        user.setEmail(userRequest.getEmail() != null ? userRequest.getEmail() : user.getEmail());
        user.setPhone(userRequest.getPhone() != null ? userRequest.getPhone() : user.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = new Address(userRequest.getAddress().getStreet(),
                    userRequest.getAddress().getCity(),
                    userRequest.getAddress().getState(),
                    userRequest.getAddress().getCountry(),
                    userRequest.getAddress().getZipcode());

            user.setAddress(address);
        }

        return user;
    }

    public static CartResponse getCartResponse(CartItem cartItem) {
        return new CartResponse(cartItem.getId(),
                getUserResponse(cartItem.getUser()),
                getProductResponse(cartItem.getProduct()),
                cartItem.getQuantity(),
                cartItem.getPrice());
    }

    public static OrderItemResponse getOrderItemResponse(OrderItem orderItem) {
        return new OrderItemResponse(orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice());
    }

    public static OrderResponse getOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse(order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt());

        List<OrderItemResponse> orderItemResponses = order.getOrderItems()
                .stream()
                .map(Mappers::getOrderItemResponse)
                .toList();

        orderResponse.setOrderItemsResponses(orderItemResponses);

        return orderResponse;
    }
}
