package com.app.daily_haul.dto;

import lombok.Data;

@Data
public class CartItemRequest {

    private Long productId;

    private Integer quantity;
}
