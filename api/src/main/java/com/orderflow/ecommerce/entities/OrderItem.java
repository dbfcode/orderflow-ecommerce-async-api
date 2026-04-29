package com.orderflow.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents a snapshot of a product at the time of purchase.
 * Unlike Product (which reflects current catalog data), OrderItem
 * preserves the price paid — so changes to the product's price
 * never affect historical orders.
 */
@Entity
@Table(name = "tb_order_item")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderItem {

    public OrderItem(Product product, Integer quantity, BigDecimal unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Reference to the catalog product (for display/reporting purposes)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    // Locked-in price at the moment of purchase — never read from product.price
    @Column(nullable = false)
    private BigDecimal unitPrice;
}