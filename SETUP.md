# Recipe Platform - Setup Guide

## Prerequisites

Before you begin, ensure you have the following installed:

- **JDK 21** - Required for Spring Boot backend
- **Node.js 22** - Required for Vue.js frontend
- **MySQL 8.0+** - Database server
- **Maven 3.6+** - For building the backend
- **Git** - For version control

## Database Setup

### 1. Create Database

```bash
# Login to MySQL
mysql -u root -p

# Run the schema script
source database/schema.sql

# Run the sample data script
source database/sample_data.sql
```

Or manually:

```sql
-- Create database
CREATE DATABASE recipe_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Import schema
USE recipe_platform;
-- Copy and paste contents from database/schema.sql

-- Import sample data
-- Copy and paste contents from database/sample_data.sql
```

### 2. Verify Database

```sql
USE recipe_platform;
SHOW TABLES;
SELECT * FROM user;
```

You should see 3 users:
- admin (password: admin123) - Administrator
- ccc (password: user123) - Regular user
- nana (password: user123) - Regular user

## Backend Setup (Spring Boot)

### 1. Configure Database Connection

Edit `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/recipe_platform?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: YOUR_MYSQL_PASSWORD  # Change this!
```

### 2. Build and Run

```bash
cd backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend API will start on `http://localhost:8080/api`

### 3. Verify Backend

Open your browser or use curl:

```bash
# Health check (if implemented)
curl http://localhost:8080/api/actuator/health

# Or check if server is running
curl http://localhost:8080/api/auth/login
```

## Frontend Setup (Vue.js)

### 1. Install Dependencies

```bash
cd frontend

# Install packages
npm install
```

### 2. Run Development Server

```bash
npm run dev
```

The frontend will start on `http://localhost:5173`

### 3. Build for Production

```bash
npm run build
```

## Testing the Application

### 1. Access the Application

Open your browser and navigate to: `http://localhost:5173`

### 2. Login Credentials

**Administrator Account:**
- Username: `admin`
- Password: `admin123`
- Access: Admin dashboard at `/admin`

**Regular User Accounts:**
- Username: `ccc` / Password: `user123`
- Username: `nana` / Password: `user123`
- Access: User homepage at `/`

### 3. Test Features

**User Features:**
- Browse homepage with carousel
- View announcements
- Browse recipes and food notes
- Search functionality
- User profile management

**Admin Features:**
- Dashboard with statistics
- User management (CRUD)
- Recipe management
- Food notes management
- Announcement management
- Data visualization charts

## Project Structure

```
recipe-platform/
├── backend/                    # Spring Boot API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/recipe/
│   │   │   │   ├── config/    # Security, CORS configuration
│   │   │   │   ├── controller/# REST API controllers
│   │   │   │   ├── dto/       # Data Transfer Objects
│   │   │   │   ├── entity/    # JPA entities
│   │   │   │   ├── repository/# Data access layer
│   │   │   │   ├── security/  # JWT authentication
│   │   │   │   └── service/   # Business logic
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   └── pom.xml
│
├── frontend/                   # Vue.js Application
│   ├── src/
│   │   ├── assets/            # Static assets
│   │   ├── components/        # Reusable components
│   │   ├── router/            # Vue Router configuration
│   │   ├── stores/            # Pinia state management
│   │   ├── utils/             # Utility functions
│   │   ├── views/             # Page components
│   │   │   ├── admin/         # Admin pages
│   │   │   └── user/          # User pages
│   │   ├── App.vue
│   │   └── main.js
│   ├── index.html
│   ├── package.json
│   └── vite.config.js
│
└── database/                   # SQL scripts
    ├── schema.sql             # Database schema
    └── sample_data.sql        # Sample data
```

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### User Endpoints (Protected)
- `GET /api/user/profile` - Get user profile
- `PUT /api/user/profile` - Update user profile
- `GET /api/recipes` - Get recipes list
- `GET /api/recipes/{id}` - Get recipe details
- `GET /api/notes` - Get food notes list
- `POST /api/recipes` - Create recipe
- `POST /api/notes` - Create food note

### Admin Endpoints (Admin Only)
- `GET /api/admin/users` - Get all users
- `POST /api/admin/users` - Create user
- `PUT /api/admin/users/{id}` - Update user
- `DELETE /api/admin/users/{id}` - Delete user
- `GET /api/admin/recipes` - Get all recipes
- `GET /api/admin/notes` - Get all notes
- `GET /api/admin/announcements` - Get announcements
- `POST /api/admin/announcements` - Create announcement

## Troubleshooting

### Backend Issues

**Port 8080 already in use:**
```bash
# Find process using port 8080
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Mac/Linux

# Kill the process or change port in application.yml
```

**Database connection failed:**
- Verify MySQL is running
- Check username/password in application.yml
- Ensure database `recipe_platform` exists
- Check firewall settings

**JWT token errors:**
- Ensure JWT secret is at least 256 bits
- Check token expiration time
- Verify Authorization header format: `Bearer <token>`

### Frontend Issues

**Port 5173 already in use:**
```bash
# Change port in vite.config.js
server: {
  port: 3000  // Use different port
}
```

**API connection failed:**
- Verify backend is running on port 8080
- Check proxy configuration in vite.config.js
- Check CORS settings in SecurityConfig.java

**Login not working:**
- Check browser console for errors
- Verify API endpoint is correct
- Check network tab for request/response
- Ensure credentials are correct

## Development Tips

### Hot Reload

**Backend:**
- Spring Boot DevTools is included
- Changes to Java files will auto-reload

**Frontend:**
- Vite provides instant HMR (Hot Module Replacement)
- Changes to Vue files update immediately

### Database Changes

After modifying database schema:
1. Update entity classes in backend
2. Restart Spring Boot application
3. Clear browser cache if needed

### Adding New Features

1. **Backend:**
   - Create entity in `entity/`
   - Create repository in `repository/`
   - Create service in `service/`
   - Create controller in `controller/`
   - Add security rules in `SecurityConfig.java`

2. **Frontend:**
   - Create view in `views/`
   - Add route in `router/index.js`
   - Create API calls in `utils/` or stores
   - Update navigation if needed

## Next Steps

1. **Implement remaining CRUD operations** for recipes, notes, categories
2. **Add file upload functionality** for images
3. **Implement search and filtering**
4. **Add data visualization** with ECharts or Chart.js
5. **Implement social features** (likes, comments, collections)
6. **Add meal planning functionality**
7. **Implement pagination** for all list views
8. **Add form validation** on both frontend and backend
9. **Write unit tests** for critical functionality
10. **Deploy to production** server

## Support

For issues or questions:
1. Check this documentation
2. Review error logs in console
3. Check browser developer tools
4. Verify all prerequisites are installed correctly

## License

This project is for educational purposes.
