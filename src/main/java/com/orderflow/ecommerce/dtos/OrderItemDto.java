package com.orderflow.ecommerce.dtos;

import java.math.BigDecimal;

public record OrderItemDto(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {
}