Maven BUILD:
mvn clean package -DskipTests

Run with docker-compose (DEV Environment)
docker compose up --build
# App at http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui/index.html

Push Docker Image
docker push docker.io/dhaval777/library-app:latest

Swger Path URL:
http://localhost:7080/swagger-ui.html

Run unit tests:
mvn test


Database choice & justification
PostgreSQL:

ACID compliance and row-level locking required to safely enforce "one borrower per book id at a time" (we use SELECT FOR UPDATE).
Mature, widely-used in production and container-friendly.
Good support in Hibernate for UUID columns.
Runs easily with Docker for development.
(For quick dev you can use H2, but Postgres is recommended for concurrency.)