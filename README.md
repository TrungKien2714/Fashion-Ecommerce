# Fashion-Ecommerce

Microservices-based e-commerce backend with Spring Boot, Spring Cloud, Kafka, Redis, and Keycloak. This repo includes a discovery server, API gateway, auth service, order service, and product service.

## Services

- `discorvery_server` (Eureka) — service discovery.
- `api_gateway` — Spring Cloud Gateway + OAuth2 resource server.
- `auth_service` — authentication/authorization (Keycloak integration).
- `Order_Service` — order management + Kafka producer.
- `Product_Service` — product management + Kafka consumer + Redis cache.

## Ports (from repo config)

- Eureka: `8761` (`discorvery_server/src/main/resources/application.yaml`)
- API Gateway: `8282` (`api_gateway/src/main/resources/application.yaml`)
- Order Service: `8082` (`Order_Service/src/main/resources/application.properties`)
- Product Service: `8888` (`Product_Service/src/main/resources/application.properties`)

## Prerequisites

- Java 21
- Docker Desktop (for Redis/Kafka/Keycloak/ELK)
- PostgreSQL (for Order/Product services)
- Maven Wrapper (included)

## Quick Start

1) Start infrastructure services (Redis, Kafka, Keycloak, etc.):

```powershell
Push-Location "/Fashion-Ecommerce"
docker compose up -d
Pop-Location
```

2) Start Eureka:

```powershell
Push-Location "Fashion-Ecommerce\discorvery_server"
.\mvnw spring-boot:run
Pop-Location
```

3) Start API Gateway:

```powershell
Push-Location "Fashion-Ecommerce\api_gateway"
.\mvnw spring-boot:run
Pop-Location
```

4) Start services:

```powershell
Push-Location "Fashion-Ecommerce\Order_Service"
.\mvnw spring-boot:run
Pop-Location

Push-Location "Fashion-Ecommerce\Product_Service"
.\mvnw spring-boot:run
Pop-Location
```

## Swagger / OpenAPI

- Order Service docs (direct): `http://localhost:8082/v3/api-docs`
- Product Service docs (direct): `http://localhost:8888/v3/api-docs`
- Gateway Swagger UI (aggregated): `http://localhost:8282/swagger-ui.html`

If Swagger UI shows 401, ensure the gateway security config allows `/swagger-ui/**` and `/v3/api-docs/**`, and that services are registered in Eureka.

## Common Endpoints

- Orders: `http://localhost:8282/v1/orders/**`
- Products: `http://localhost:8282/v1/products/**`

## Notes

- PostgreSQL connection strings are in `Order_Service/src/main/resources/application.properties` and `Product_Service/src/main/resources/application.properties`.
- Redis configuration for `Product_Service` is also in `Product_Service/src/main/resources/application.properties`.
- Keycloak runs via Docker on port `8085` (see `docker-compose.yml`).

## Troubleshooting

- Eureka not running: gateway routes (`lb://...`) will fail with 503.
- `/v3/api-docs` 500 on Order Service: ensure Springdoc version is `2.8.0` or higher.

## Repo Structure

```
Fashion-Ecommerce/
  api_gateway/
  auth_service/
  discorvery_server/
  Order_Service/
  Product_Service/
  docker-compose.yml
```
