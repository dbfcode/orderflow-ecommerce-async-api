# Monorepo — frontend e API

Este repositório contém:

| Pasta | Descrição |
|-------|-----------|
| [`api/`](api/) | Spring Boot (OrderFlow Commerce) |
| [`web/`](web/) | Frontend Vite + React |

## Subir tudo com Docker

Na **raiz** do repositório:

```bash
docker compose up --build
```

- API: http://localhost:8080  
- Swagger: http://localhost:8080/swagger-ui.html  
- Web (nginx): http://localhost:4173  
- RabbitMQ UI: http://localhost:15672  

## Desenvolvimento local (sem Docker Compose completo)

- **API:** `cd api && ./mvnw spring-boot:run` (Windows: `mvnw.cmd`)  
- **Web:** `cd web && npm install && npm run dev`  

Detalhes da API, stack e variáveis: [`api/README.md`](api/README.md).
