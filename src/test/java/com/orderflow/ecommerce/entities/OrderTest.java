package com.orderflow.ecommerce.entities;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    @Test
    void shouldCalculateTotalCorrectly() {
        Product product = new Product(null, "Camiseta", null, BigDecimal.valueOf(49.90), 10, null);
        OrderItem item = new OrderItem(product, 2, product.getPrice());
        Order order = new Order(List.of(item));
        assertEquals(BigDecimal.valueOf(99.80), order.getTotal());
    }
}