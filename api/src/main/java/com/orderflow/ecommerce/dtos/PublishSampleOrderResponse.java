package com.orderflow.ecommerce.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resultado da publicação de um evento OrderCreated de exemplo no RabbitMQ")
public record PublishSampleOrderResponse(
        @Schema(description = "Indica se a publicação no broker foi solicitada com sucesso")
        boolean published,
        @Schema(description = "Identificador do pedido de exemplo usado no evento", example = "1001")
        long orderId,
        @Schema(description = "Dica para validar consumidores (logs EmailConsumer e InventoryConsumer)")
        String hint
) {
}
