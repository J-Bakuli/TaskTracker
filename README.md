# Task Tracker
Backend for a task tracker (users, tasks, email, summarization).

## Stack
- Java 21, Spring Boot, Maven
- PostgreSQL, Kafka
- Docker Compose, Liquibase

## Quick start
1. In `task-tracker-backend/` create gitignored `.env` (Compose) and `application-local.properties` (JDBC).
Spring loads the latter via `spring.config.import` from the backend working directory.
2. From `task-tracker-backend/`: `docker compose up -d` (Postgres + Kafka). App is not in Compose.
3. Run the backend from the same directory (`mvnw spring-boot:run` or IDE). JDBC URL must match the published Postgres port from `.env`.
4. Schema is applied by Liquibase on startup (`ddl-auto=validate`). `docker compose down` keeps volumes.
Command `down -v` wipes Postgres/Kafka data — use only when you want an empty database, not daily.
