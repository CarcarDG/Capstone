# Backend Connection Checklist ✅

## Quick Setup (5 Steps)

### ☐ Step 1: Install Prerequisites
- [ ] Java 17+ installed (`java -version`)
- [ ] Maven 3.6+ installed (`mvn -version`)
- [ ] MySQL 8.0+ installed and running
- [ ] Node.js 18+ installed (`node --version`)

### ☐ Step 2: Setup Database
```powershell
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE recipe_platform;
USE recipe_platform;

# Run schema (exit MySQL first)
exit
mysql -u root -p recipe_platform < database/schema.sql
mysql -u root -p recipe_platform < database/sample_data.sql
```

### ☐ Step 3: Configure Backend
Edit `backend/src/main/resources/application.properties`:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```
Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL password!

### ☐ Step 4: Start Backend
```powershell
cd backend
mvn spring-boot:run
```
Wait for: "Started RecipeApplication"

### ☐ Step 5: Start Frontend
```powershell
cd frontend
npm run dev
```

## ✅ Verification

Test login at http://localhost:5173:
- Username: `admin`
- Password: `admin123`

If login works without "Network error" → **Success!** 🎉

## 🚀 Quick Start Script

Double-click: `QUICK_START.bat`

This will:
1. Start MySQL (if not running)
2. Start Backend in new terminal
3. Start Frontend in new terminal

## 📖 Detailed Guide

See `BACKEND_CONNECTION_GUIDE.md` for:
- Detailed troubleshooting
- API testing
- Configuration options
- Common issues and solutions

## 🆘 Common Issues

**"Cannot connect to database"**
→ Check MySQL is running: `net start MySQL80`

**"Port 8080 already in use"**
→ Kill process: `netstat -ano | findstr :8080`

**"mvn command not found"**
→ Install Maven and add to PATH

**Frontend still shows "Network error"**
→ Backend might not be running. Check terminal for errors.

## 📝 Test Accounts

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| john | user123 | USER |
| jane | user123 | USER |
