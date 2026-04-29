package com.orderflow.ecommerce.messaging.events;

import com.orderflow.ecommerce.dtos.OrderItemDto;
import com.orderflow.ecommerce.entities.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEvent(
        Long orderId,
        LocalDateTime createdAt,
        OrderStatus status,
        BigDecimal total,
        List<OrderItemDto> items
) {
}
