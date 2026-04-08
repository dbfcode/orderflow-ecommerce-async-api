package com.orderflow.ecommerce.dtos;

import com.orderflow.ecommerce.entities.CartItem;

public record CartItemResponse(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        Double price
) {
    public static CartItemResponse from(CartItem item) {
        return new CartItemResponse(
                item.getId(),
                item.getProductId(),
                "Produto #" + item.getProductId(),
                item.getQuantity(),
                item.getPrice()
        );
    }
}