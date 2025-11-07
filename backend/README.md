# Recipe Platform Backend

Spring Boot REST API for Recipe and Food Sharing Platform.

## Technology Stack

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Security** with JWT authentication
- **Spring Data JPA** for database access
- **MySQL** database
- **Maven** for dependency management

## Quick Start

### Prerequisites

- JDK 21
- Maven 3.6+
- MySQL 8.0+

### Setup

1. Create MySQL database:
```sql
CREATE DATABASE recipe_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Configure database connection in `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/recipe_platform
    username: root
    password: your_password
```

3. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api`

## API Documentation

### Authentication

**Login**
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

Response:
```json
{
  "code": 200,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "admin",
    "nickname": "管理员",
    "role": "ADMIN",
    "avatar": "/avatars/admin.jpg"
  }
}
```

**Register**
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "password": "password123",
  "nickname": "New User",
  "email": "user@example.com"
}
```

### Protected Endpoints

All other endpoints require JWT token in Authorization header:
```http
Authorization: Bearer <your-jwt-token>
```

## Project Structure

```
src/main/java/com/recipe/
├── config/
│   └── SecurityConfig.java          # Security configuration
├── controller/
│   └── AuthController.java          # Authentication endpoints
├── dto/
│   ├── ApiResponse.java             # Standard API response
│   ├── LoginRequest.java            # Login request DTO
│   └── LoginResponse.java           # Login response DTO
├── entity/
│   └── User.java                    # User entity
├── repository/
│   └── UserRepository.java          # User data access
├── security/
│   ├── JwtUtil.java                 # JWT utility
│   └── JwtAuthenticationFilter.java # JWT filter
├── service/
│   ├── AuthService.java             # Authentication service
│   └── UserDetailsServiceImpl.java  # User details service
└── RecipePlatformApplication.java   # Main application
```

## Security

- JWT-based authentication
- BCrypt password encoding
- Role-based access control (USER, ADMIN)
- CORS enabled for frontend
- Stateless session management

## Database Schema

See `../../database/schema.sql` for complete database structure.

Main tables:
- `user` - User accounts
- `recipe` - Recipe information
- `recipe_category` - Recipe categories
- `food_note` - Food diaries/notes
- `comment` - Comments on recipes/notes
- `collection` - User favorites
- `user_like` - User likes
- `meal_plan` - Weekly meal planning
- `announcement` - System announcements
- `carousel` - Homepage carousel items

## Development

### Running Tests

```bash
mvn test
```

### Building for Production

```bash
mvn clean package
java -jar target/recipe-platform-1.0.0.jar
```

### Environment Variables

You can override configuration using environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/recipe_platform
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=password
export JWT_SECRET=your-secret-key
```

## Next Steps

1. Implement recipe CRUD operations
2. Implement food note CRUD operations
3. Add file upload for images
4. Implement search functionality
5. Add pagination support
6. Implement social features (likes, comments)
7. Add admin management endpoints
8. Write comprehensive tests
