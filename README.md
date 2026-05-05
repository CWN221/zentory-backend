# InventoryAppBackend (Backend)

This module is the Spring Boot REST API for the Zentory Inventory system. It receives sync batches from clients and persists data in a MySQL database.

## Prerequisites
- Java 17 (JDK)
- Maven
- A MySQL database

## Build
```powershell
cd InventoryAppBackend
mvn -DskipTests package
```

## Important environment variables
- `MYSQL_URL` — JDBC URL used by Spring Boot datasource
- `MYSQL_USER` — DB username
- `MYSQL_PASSWORD` — DB password

## Key endpoints
- `GET /api/sync/health` — basic healthcheck
- `POST /api/sync/batch` — accept client sync batches
- `GET /api/sync/changes` — fetch changes
- `POST /api/auth/register` — register a user
- `POST /api/auth/login` — login and receive JWT


## Schema generation
- Hibernate will auto-create tables for JPA entities when `spring.jpa.hibernate.ddl-auto=create` is set in `application.properties`. 

## Troubleshooting
- No tables created: ensure the backend connects to MySQL (check env vars) and that JPA entities are present.
- 401 on healthcheck: ensure the deployed jar permits (`/api/sync/**`) and that platform health point to `/api/sync/health`.