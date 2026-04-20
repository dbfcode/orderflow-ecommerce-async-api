package com.orderflow.ecommerce.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddItemRequest(
        @NotNull Long productId,
        @NotNull @Positive Integer quantity,
        @NotNull @Positive Double price
) {

}