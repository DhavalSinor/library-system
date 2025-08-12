###Build the Application (skip tests)
mvn clean package -DskipTests


###Run with Docker Compose (DEV Environment)
docker compose up --build -d

#App: http://localhost:8080
#Swagger UI (Rest API Docs): http://localhost:8080/swagger-ui/index.html

###Database Choice###
1. ACID compliance and row-level locking (SELECT FOR UPDATE) ensure safe concurrent operations — e.g., enforcing “one borrower per book ID at a time”.
2. Mature, widely used in production, and well-supported in Docker.
3. Excellent Hibernate support for UUID columns.
4. Easy to spin up locally with Docker.

###Tech Stack
1. Java 17
2. Spring Boot 3
3. Spring Data JPA + Hibernate
4. PostgreSQL (prod/dev) / H2 (optional dev)
5. Docker + Docker Compose
6. Swagger/OpenAPI for API documentation
