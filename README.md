# JavaEduApp Backend

Spring Boot REST API for an education management app: HR (employees, departments, attendance, leave, holidays), a question bank (subjects, grades, questions, blueprints, academic events), notifications, audit logging, and role-based users with JWT authentication.

## Tech stack

- Java 21, Spring Boot 3.3.5
- Spring Web, Data JPA, Security, Validation, Mail
- MySQL
- JWT (jjwt)
- springdoc OpenAPI / Swagger UI
- Maven

## Project structure

```
src/main/java/com/javaeduapp/
├── auth/          Login, JWT service and filter
├── user/          Users and roles
├── hr/            Employees, departments, designations, attendance, leave, holidays
├── questionbank/  Subjects, grades, questions, blueprints, academic events
├── notification/  Notifications
├── audit/         Audit events
├── config/        Security, CORS, SPA config, admin password initializer
└── common/        Shared response, base entity, exception handling
db/setup.sql       MySQL database/user setup script
```

## Getting started

### Prerequisites

- JDK 21
- Maven 3.9+
- MySQL 8+

### 1. Database

The app expects a MySQL database `lms` on `localhost:3306` and a user `lms`. Create them, for example:

```sql
CREATE DATABASE IF NOT EXISTS lms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'lms'@'localhost' IDENTIFIED BY 'your-password';
GRANT ALL PRIVILEGES ON lms.* TO 'lms'@'localhost';
```

(`db/setup.sql` is an alternative script that targets a database named `eduapp`; adjust `spring.datasource.url` if you use it.)

Tables are created automatically (`spring.jpa.hibernate.ddl-auto=update`) and seed data is loaded from `data.sql`.

### 2. Configuration

Settings come from environment variables:

| Variable         | Purpose                                              | Default                          |
|------------------|------------------------------------------------------|----------------------------------|
| `DB_PASSWORD`    | Password for the MySQL `lms` user                    | empty                            |
| `JWT_SECRET`     | JWT signing secret (at least 32 characters)          | insecure development placeholder |
| `ADMIN_PASSWORD` | If set, sets the seeded `admin` user's password      | not set                          |
| `FRONTEND_URL`   | Allowed frontend origin for CORS                     | `http://localhost:5173`          |

Always set `JWT_SECRET` and `ADMIN_PASSWORD` outside local development.

### 3. Run

```bash
# Linux/macOS
export DB_PASSWORD=your-password
mvn spring-boot:run
```

```powershell
# Windows PowerShell
$env:DB_PASSWORD = "your-password"
mvn spring-boot:run
```

The API runs on http://localhost:8080.

## API docs

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

## Build

```bash
mvn clean package
java -jar target/javaeduapp-backend-0.0.1-SNAPSHOT.jar
```
