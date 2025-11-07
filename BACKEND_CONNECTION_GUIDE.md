# Backend Connection Guide

## 📋 Prerequisites

Before connecting the backend, ensure you have:

### Required Software
- ✅ **Java 17 or higher** - [Download](https://adoptium.net/)
- ✅ **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- ✅ **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/mysql/)
- ✅ **Node.js 18+** - Already installed

### Check Installations
```powershell
# Check Java
java -version

# Check Maven
mvn -version

# Check MySQL
mysql --version

# Check Node
node --version
```

---

## 🗄️ Step 1: Setup Database

### 1.1 Start MySQL Service
```powershell
# Start MySQL service (Windows)
net start MySQL80

# Or use MySQL Workbench to start the service
```

### 1.2 Create Database
```powershell
# Login to MySQL
mysql -u root -p

# In MySQL prompt, run:
CREATE DATABASE recipe_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE recipe_platform;
```

### 1.3 Run Schema Script
**Option A: Using MySQL Command Line**
```powershell
# Exit MySQL prompt first (type 'exit')
# Then run:
mysql -u root -p recipe_platform < database/schema.sql
```

**Option B: Using MySQL Workbench (Easier)**
1. Open MySQL Workbench
2. Connect to your database
3. File → Open SQL Script → Select `database/schema.sql`
4. Click Execute (⚡ icon)
5. File → Open SQL Script → Select `database/sample_data.sql`
6. Click Execute (⚡ icon)

### 1.4 Verify Database
```sql
USE recipe_platform;
SHOW TABLES;
SELECT * FROM user;
```

You should see tables and sample data.

---

## ⚙️ Step 2: Configure Backend

### 2.1 Update Database Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/recipe_platform?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

# JPA Configuration
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=8080

# JWT Configuration
jwt.secret=your-secret-key-change-this-in-production-make-it-long-and-secure
jwt.expiration=86400000

# CORS Configuration
cors.allowed-origins=http://localhost:5173
```

**Important:** Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL root password!

### 2.2 Verify Backend Files

Make sure these files exist:
- ✅ `backend/src/main/java/com/recipe/controller/AuthController.java`
- ✅ `backend/src/main/java/com/recipe/dto/LoginRequest.java`
- ✅ `backend/src/main/java/com/recipe/dto/LoginResponse.java`
- ✅ `backend/src/main/java/com/recipe/service/AuthService.java`
- ✅ `backend/src/main/java/com/recipe/security/JwtUtil.java`
- ✅ `backend/src/main/java/com/recipe/config/SecurityConfig.java`

---

## 🚀 Step 3: Start Backend

### 3.1 Build the Project
```powershell
cd backend
mvn clean install
```

If you see errors, try:
```powershell
mvn clean install -DskipTests
```

### 3.2 Run the Backend
```powershell
mvn spring-boot:run
```

**Expected Output:**
```
Started RecipeApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

### 3.3 Verify Backend is Running

Open browser or use curl:
```
http://localhost:8080/api/auth/login
```

You should see a response (even if it's an error, it means the server is running).

---

## 🎨 Step 4: Connect Frontend

### 4.1 Verify Environment Configuration

Check `frontend/.env.development`:
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

This should already be set correctly.

### 4.2 Start Frontend
```powershell
cd frontend
npm run dev
```

### 4.3 Test the Connection

1. Open browser: `http://localhost:5173`
2. Go to Login page
3. Try logging in with:
   - **Username:** `admin`
   - **Password:** `admin123`

**If successful:** You'll see "Login successful" and be redirected to the home page.

**If it fails:** Check the browser console (F12) for errors.

---

## 🧪 Step 5: Test API Endpoints

### Using Browser Console

Open browser console (F12) and run:

```javascript
// Test Login
fetch('http://localhost:8080/api/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ username: 'admin', password: 'admin123' })
})
.then(res => res.json())
.then(data => console.log('Login response:', data))
.catch(err => console.error('Error:', err))
```

### Using Postman

**Login Request:**
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "admin",
  "nickname": "Administrator",
  "role": "ADMIN",
  "avatar": "https://i.pravatar.cc/200?img=12"
}
```

---

## 🔧 Troubleshooting

### Problem: "Cannot connect to database"

**Solution:**
1. Check MySQL is running: `net start MySQL80`
2. Verify credentials in `application.properties`
3. Test connection: `mysql -u root -p`

### Problem: "Port 8080 already in use"

**Solution:**
```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process
taskkill /PID <PID_NUMBER> /F

# Or change port in application.properties
server.port=8081
```

### Problem: "mvn command not found"

**Solution:**
1. Install Maven: https://maven.apache.org/download.cgi
2. Add to PATH environment variable
3. Restart terminal

### Problem: "Frontend still using mock auth"

**Solution:**
The app automatically falls back to mock auth if backend is unavailable. This is by design!

To verify it's using real backend:
1. Check browser Network tab (F12)
2. Look for requests to `http://localhost:8080/api/auth/login`
3. If you see these requests, it's trying to connect to backend

### Problem: "CORS errors"

**Solution:**
Update `SecurityConfig.java`:
```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

---

## 📊 Verify Everything is Working

### Checklist

- [ ] MySQL service is running
- [ ] Database `recipe_platform` exists
- [ ] Tables are created (run `SHOW TABLES;`)
- [ ] Sample data is loaded (run `SELECT * FROM user;`)
- [ ] Backend is running on port 8080
- [ ] Frontend is running on port 5173
- [ ] Can login with `admin/admin123`
- [ ] Can see user data in browser

### Test All Features

1. **Login** - Try admin and user accounts
2. **Signup** - Create a new account
3. **Profile** - View and edit profile
4. **Recipes** - Browse recipes
5. **Notes** - View food notes
6. **Collections** - Save items
7. **Meal Planner** - Plan meals

---

## 🎯 Quick Start Commands

### Start Everything (3 Terminals)

**Terminal 1 - Database:**
```powershell
# Start MySQL (if not running)
net start MySQL80
```

**Terminal 2 - Backend:**
```powershell
cd backend
mvn spring-boot:run
```

**Terminal 3 - Frontend:**
```powershell
cd frontend
npm run dev
```

### Stop Everything

- **Frontend:** Press `Ctrl + C` in terminal
- **Backend:** Press `Ctrl + C` in terminal
- **MySQL:** `net stop MySQL80` (optional)

---

## 📝 Environment Variables Summary

### Frontend (.env.development)
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

### Backend (application.properties)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recipe_platform
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
server.port=8080
jwt.secret=your-secret-key
```

---

## 🔐 Default Test Accounts

After loading sample data:

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| john | user123 | USER |
| jane | user123 | USER |

---

## 📚 Additional Resources

- **Spring Boot Docs:** https://spring.io/projects/spring-boot
- **MySQL Docs:** https://dev.mysql.com/doc/
- **Vue.js Docs:** https://vuejs.org/
- **Element Plus:** https://element-plus.org/

---

## 🆘 Need Help?

1. Check backend logs in terminal
2. Check browser console (F12)
3. Verify all services are running
4. Check database connection
5. Review error messages carefully

---

## ✅ Success Indicators

You'll know everything is working when:

1. ✅ Backend shows "Started RecipeApplication"
2. ✅ Frontend shows "Local: http://localhost:5173"
3. ✅ Login works without "Network error"
4. ✅ Browser Network tab shows API calls to localhost:8080
5. ✅ User data persists after page refresh

**Congratulations! Your full-stack application is now connected! 🎉**
