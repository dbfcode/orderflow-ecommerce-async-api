OrderFlow Commerce
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-green.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.13-orange.svg)
![Redis](https://img.shields.io/badge/Redis-7.2-red.svg)
![Docker](https://img.shields.io/badge/Docker-27.3-blue.svg)
OrderFlow Commerce is an event-driven e-commerce REST API built with Java 21 and Spring Boot.  
It simulates a real-world microservices architecture by processing orders asynchronously using RabbitMQ. After checkout, orders are handled in the background – inventory is reserved and confirmation emails are sent – without blocking the client.
---
🚀 Features
Product & Category Management – CRUD operations with relational mapping
User Authentication – JWT-based security with role support (`USER`, `ADMIN`)
Shopping Cart – Persistent cart in PostgreSQL with hybrid caching (Redis)
Checkout & Order Processing – Order creation with event publication
Event-Driven Architecture – Decoupled order processing via RabbitMQ
Resilience – Circuit breaker pattern with Resilience4j
Containerized Environment – Run everything with Docker Compose
API Documentation – Interactive Swagger UI
---
🛠️ Tech Stack
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
📦 Architecture Overview
The system is built as a single Spring Boot application but follows an event-driven design:
User interacts with REST endpoints (products, cart, checkout).
Checkout creates an order and publishes an `OrderCreated` event to RabbitMQ.
Consumers (inventory & email) process the event asynchronously:
Inventory consumer reserves items (simulated).
Email consumer sends a confirmation email.
This decouples the core order creation from time-consuming operations, improving responsiveness and scalability.
![Architecture Diagram](docs/architecture.png)  
(Diagram will be added soon)
---
🧪 How to Run (Development)
Prerequisites
Docker Engine 24+ and Docker Compose
Git
Steps
Clone the repository
```bash
   git clone https://github.com/dbfcode/orderflow-ecommerce-async-api.git
   cd orderflow-ecommerce-async-api
   ```
Start the environment
```bash
   docker-compose up --build
   ```
This command builds the application image and starts all containers:
PostgreSQL on port 5432
RabbitMQ on ports 5672 (AMQP) and 15672 (Management UI)
Spring Boot app on port 8080
Access the services
Swagger UI: http://localhost:8080/swagger-ui.html
RabbitMQ Management: http://localhost:15672 (user: orderflow / password: orderflow123)
PostgreSQL: localhost:5432 (database: orderflow, user: orderflow / password: orderflow123)
Stop the containers
```bash
   docker-compose down
   ```
> Note: The application runs in development mode with spring-boot-devtools. Code changes are automatically reloaded inside the container. If you modify `pom.xml` or the Dockerfile, rebuild with `docker-compose up --build`.
---
📖 API Documentation
Once the application is running, visit Swagger UI to explore all endpoints. A Postman collection is also available in the `/docs` folder.
---
👥 Contributors
Name	Role	Main Contributions
Diego Ferreira	Advanced Developer	Messaging layer (RabbitMQ), checkout, Docker infrastructure, Redis caching, circuit breaker, integration tests
Giovanna Caxias	Junior Developer	Product, category, user CRUD; JWT authentication; shopping cart (PostgreSQL); Swagger documentation
---
📄 License
This project is for portfolio purposes and is not licensed for commercial use.
---
🤝 Contributing
We are not accepting external contributions at this moment, but feel free to fork the project and experiment.
---
Built with ☕ and 🐳
