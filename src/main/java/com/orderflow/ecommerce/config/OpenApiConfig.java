package com.orderflow.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI orderflowOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("OrderFlow Commerce API")
                        .version("1.0-SNAPSHOT")
                        .description("""
                                API REST de e-commerce com processamento assíncrono via RabbitMQ. \
                                Documentação gerada com OpenAPI 3; use o Swagger UI para explorar os endpoints.""")
                        .contact(new Contact().name("OrderFlow").url("https://github.com/dbfcode/orderflow-ecommerce-async-api")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("API exposta na porta 8080 (Docker Compose ou execução local).")));
    }
}
