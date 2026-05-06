package com.orderflow.ecommerce.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Envelope padrão de erro retornado pelo GlobalExceptionHandler")
public record ErrorResponse(
        @Schema(description = "Instante em que o erro foi gerado (UTC)")
        Instant timestamp,
        @Schema(description = "Código HTTP", example = "404")
        Integer status,
        @Schema(description = "Mensagem detalhando o erro ou validação")
        String message,
        @Schema(description = "Caminho da requisição que originou o erro", example = "/categories/99")
        String path
) {
}