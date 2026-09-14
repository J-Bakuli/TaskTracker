# Task Tracker
Backend for a task tracker (users, tasks, email, summarization).

## Stack
- Java 21, Spring Boot, Maven
- PostgreSQL, Kafka
- Docker Compose, Liquibase

## Quick start
1. In `task-tracker-backend/` create `.env` and `application-local.properties` (both gitignored).
2. From that folder: `docker compose up -d`
3. Start the backend from the same folder (IDE or `mvnw spring-boot:run`).
4. Liquibase creates the schema on startup.
