package com.app.daily_haul.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

    private Long id;

    private UserResponse user;

    private ProductResponse product;

    private Integer quantity;

    private BigDecimal price;
}
