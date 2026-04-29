# OrderFlow Commerce

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-green.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.13-orange.svg)
![Redis](https://img.shields.io/badge/Redis-7.2-red.svg)
![Docker](https://img.shields.io/badge/Docker-27.3-blue.svg)

OrderFlow Commerce is an event-driven e-commerce REST API built with Java 21 and Spring Boot.

It simulates a real-world microservices architecture by processing orders asynchronously using RabbitMQ.
After checkout, orders are handled in the background — inventory is reserved and confirmation emails are sent — without blocking the client.

---
### Features

Product & Category Management: CRUD operations with relational mapping
User Authentication: JWT-based security with role support (`USER`, `ADMIN`)
Shopping Cart: Persistent cart in PostgreSQL with hybrid caching (Redis)
Checkout & Order Processing: Order creation with event publication
Event-Driven Architecture: Decoupled order processing via RabbitMQ
Resilience: Circuit breaker pattern with Resilience4j
Containerized Environment: Run everything with Docker Compose
API Documentation: Interactive Swagger UI

---
### Tech Stack

Layer	Technology
Language	Java 21
Framework	Spring Boot 3
Database	PostgreSQL 15
ORM	Spring Data JPA
Security	Spring Security + JWT
Messaging	RabbitMQ
Caching	Redis
Resilience	Resilience4j
Containerization	Docker Compose
Testing	JUnit 5, Mockito, Testcontainers

---
### Architecture Overview

The system is built as a single Spring Boot application but follows an event-driven design.
Flow
User interacts with REST endpoints (products, cart, checkout)
Checkout creates an order and publishes an `OrderCreated` event to RabbitMQ
Consumers process the event asynchronously:
Inventory consumer reserves items (simulated)
Email consumer sends a confirmation email
This decouples order creation from time-consuming operations, improving responsiveness and scalability.

![Architecture Diagram](docs/architecture.png)
> Diagram will be added soon

### Microservices Migration (In Progress)

The backend is versioned in this monorepo (`api/`) alongside the web app (`web/`). Long term, services may still be split into multiple deployable units; the roadmap below describes that target.

- Isolation strategy: ownership by schema/table per service
- Integration strategy: API + RabbitMQ events (no direct cross-service joins)
- Local orchestration strategy: `docker compose` at the **repository root** (Postgres, RabbitMQ, API, web)

Detailed plan and governance:

- [`docs/microservices-migration.md`](docs/microservices-migration.md)
- [`docs/local-dev-shared-db.md`](docs/local-dev-shared-db.md)
- [`docs/sql/init-schemas.sql`](docs/sql/init-schemas.sql)
---
### How to Run (Development)

Prerequisites
Docker Engine 24+
Docker Compose
Git
Steps
1. Clone the repository
```bash
git clone https://github.com/dbfcode/orderflow-ecommerce-async-api.git
cd orderflow-ecommerce-async-api
```
The repo root contains `api/` (this service) and `web/` (frontend).

2. Start the environment **from the repository root** (not from `api/`):
```bash
docker compose up --build
```
Builds the API and web images
Starts all containers:
PostgreSQL → 5432
RabbitMQ → 5672 (AMQP), 15672 (UI)
Spring Boot → 8080
Web (nginx) → 4173
3. Access the services
Swagger UI: http://localhost:8080/swagger-ui.html
RabbitMQ: http://localhost:15672  
user: orderflow  
password: orderflow123
PostgreSQL:
host: localhost
port: 5432
database: orderflow
user: orderflow
password: orderflow123
4. Stop containers
```bash
docker compose down
```
> Uses spring-boot-devtools for auto-reload.  
> Rebuild if you change Dockerfile or pom.xml.
---

### API Documentation

Access Swagger UI after starting the application.  
Postman collection is available in `/docs`.

---

### Contributors

| Name            | Role               | Contributions                          |
|-----------------|--------------------|----------------------------------------|
| Diego Ferreira  | Advanced Developer | RabbitMQ, checkout, Docker, Redis, resilience |
| Giovanna Caxias | Junior Developer   | CRUD, JWT auth, cart, Swagger          |
---

### License
Portfolio project. Not licensed for commercial use.

---

### Contributing
Not accepting external contributions. Feel free to fork.

---
Built with Java and Docker
