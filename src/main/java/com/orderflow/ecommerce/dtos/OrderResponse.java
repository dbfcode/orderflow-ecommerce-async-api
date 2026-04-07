package com.orderflow.ecommerce.dtos;

import com.orderflow.ecommerce.entities.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        LocalDateTime createdAt,
        OrderStatus status,
        BigDecimal total,
        List<OrderItemDto> items
) {
}