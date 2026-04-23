package com.orderflow.ecommerce.dtos;

import java.time.Instant;

public record ErrorResponse(
        Instant timestamp,
        Integer status,
        String message,
        String path
) {
}