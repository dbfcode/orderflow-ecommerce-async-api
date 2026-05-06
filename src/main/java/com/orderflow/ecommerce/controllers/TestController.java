package com.orderflow.ecommerce.controllers;

import com.orderflow.ecommerce.dtos.ErrorResponse;
import com.orderflow.ecommerce.dtos.OrderItemDto;
import com.orderflow.ecommerce.dtos.PingResponse;
import com.orderflow.ecommerce.dtos.PublishSampleOrderResponse;
import com.orderflow.ecommerce.entities.enums.OrderStatus;
import com.orderflow.ecommerce.messaging.events.OrderCreatedEvent;
import com.orderflow.ecommerce.messaging.publisher.OrderEventPublisher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/test")
@Tag(name = "Testes e integração", description = "Endpoints auxiliares para health check e publicação de eventos de exemplo no RabbitMQ")
public class TestController {

    private static final long DEFAULT_START_ORDER_ID = 1000L;
    private final OrderEventPublisher orderEventPublisher;
    private final AtomicLong sampleOrderSequence = new AtomicLong(resolveInitialOrderId());

    public TestController(OrderEventPublisher orderEventPublisher) {
        this.orderEventPublisher = orderEventPublisher;
    }

    @GetMapping("/ping")
    @Operation(summary = "Verifica se a API está respondendo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "API ativa",
                    content = @Content(schema = @Schema(implementation = PingResponse.class)))
    })
    public PingResponse ping() {
        return new PingResponse(
                "ok",
                "versão 1.",
                LocalDateTime.now().toString()
        );
    }

    @GetMapping("/publish-sample-order")
    @Operation(summary = "Publica um evento OrderCreated de exemplo no RabbitMQ",
            description = "Útil para validar filas e consumidores (EmailConsumer, InventoryConsumer) sem fluxo real de pedido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento enfileirado",
                    content = @Content(schema = @Schema(implementation = PublishSampleOrderResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao publicar",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public PublishSampleOrderResponse publishSampleOrder() {
        long nextOrderId = nextSampleOrderId();
        var event = new OrderCreatedEvent(
                nextOrderId,
                LocalDateTime.now(),
                OrderStatus.PENDING,
                new BigDecimal("42.00"),
                List.of(new OrderItemDto(1L, "Produto teste", 1, new BigDecimal("42.00"), new BigDecimal("42.00")))
        );
        orderEventPublisher.publishOrderCreated(event);
        return new PublishSampleOrderResponse(
                true,
                event.orderId(),
                "Vê os logs: EmailConsumer e InventoryConsumer devem registar uma linha cada."
        );
    }

    private long nextSampleOrderId() {
        long current = sampleOrderSequence.getAndUpdate(previous ->
                previous >= Long.MAX_VALUE ? resolveFallbackOrderId() : previous + 1
        );
        if (current <= 0) {
            return resolveFallbackOrderId();
        }
        return current;
    }

    /**
     * Reads an optional initial value and falls back to DEFAULT_START_ORDER_ID when missing or invalid.
     */
    private long resolveInitialOrderId() {
        String configured = System.getProperty("orderflow.sample-order.start-id");
        if (configured == null || configured.isBlank()) {
            return DEFAULT_START_ORDER_ID;
        }
        try {
            long parsed = Long.parseLong(configured.trim());
            return parsed > 0 ? parsed : DEFAULT_START_ORDER_ID;
        } catch (NumberFormatException ignored) {
            return DEFAULT_START_ORDER_ID;
        }
    }

    private long resolveFallbackOrderId() {
        long fallback = Math.abs(System.currentTimeMillis());
        return fallback == 0 ? DEFAULT_START_ORDER_ID : fallback;
    }
}
