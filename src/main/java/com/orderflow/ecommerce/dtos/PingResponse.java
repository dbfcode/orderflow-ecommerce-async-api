package com.orderflow.ecommerce.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de verificação de disponibilidade da API")
public record PingResponse(
        @Schema(description = "Estado da API", example = "ok")
        String status,
        @Schema(description = "Mensagem descritiva", example = "versão 1.")
        String message,
        @Schema(description = "Data e hora do servidor em formato ISO-8601 (string)")
        String timestamp
) {
}
