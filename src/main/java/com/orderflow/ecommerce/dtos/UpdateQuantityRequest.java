package com.orderflow.ecommerce.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateQuantityRequest(
        @NotNull(message = "A quantidade não pode ser nula")
        @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
        Integer quantity
) {}