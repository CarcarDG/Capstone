# Quick Start Guide - Recipe Platform

## 🚀 Start the Application (Frontend Only - No Backend Needed!)

Since you're using the mock authentication, you can run just the frontend without setting up the database or backend.

### Step 1: Navigate to Frontend Folder
```powershell
cd D:\carso\Documents\Capstone\frontend
```

### Step 2: Install Dependencies (First Time Only)
```powershell
npm install
```

### Step 3: Start the Development Server
```powershell
npm run dev
```

### Step 4: Open in Browser
The app will automatically open at: **http://localhost:5173**

Or manually open your browser and go to: `http://localhost:5173`

### Step 5: Login
Use these demo accounts:
- **Admin**: username: `admin`, password: `admin123`
- **User**: username: `john`, password: `user123`

---

## 📊 If You Want to Set Up the Full Stack (Optional)

### Database Setup (MySQL Required)

#### Option 1: Using MySQL Command Line
```powershell
# Login to MySQL first
mysql -u root -p

# Then inside MySQL, run:
source D:/carso/Documents/Capstone/database/schema.sql
source D:/carso/Documents/Capstone/database/sample_data.sql
```

#### Option 2: Using MySQL Workbench (Easier)
1. Open MySQL Workbench
2. Connect to your database
3. File → Open SQL Script → Select `schema.sql`
4. Click Execute (⚡ icon)
5. File → Open SQL Script → Select `sample_data.sql`
6. Click Execute (⚡ icon)

### Backend Setup (Java + Maven Required)

#### Prerequisites Check:
```powershell
# Check if Java is installed
java -version

# Check if Maven is installed
mvn -version
```

#### If Maven is Not Installed:
1. Download from: https://maven.apache.org/download.cgi
2. Or install via Chocolatey: `choco install maven`
3. Or use the Maven wrapper: `.\mvnw spring-boot:run` (if available)

#### Start Backend:
```powershell
cd D:\carso\Documents\Capstone\backend
mvn spring-boot:run
```

Backend will run on: **http://localhost:8080**

---

## 🎯 Recommended: Frontend Only (Easiest)

For development and testing, just run the frontend:

```powershell
cd D:\carso\Documents\Capstone\frontend
npm install
npm run dev
```

The app uses mock authentication, so you don't need the backend or database to test the UI!

---

## 🐛 Troubleshooting

### "npm not found"
Install Node.js from: https://nodejs.org/

### "mvn not found"
You don't need Maven if you're only running the frontend!

### "MySQL error"
Skip the database setup and use the frontend with mock data.

### Port already in use
```powershell
# Kill process on port 5173
netstat -ano | findstr :5173
taskkill /PID <PID_NUMBER> /F
```

---

## 📝 Quick Commands Reference

```powershell
# Frontend
cd frontend
npm install          # Install dependencies (first time)
npm run dev          # Start dev server
npm run build        # Build for production

# Backend (if needed)
cd backend
mvn clean install    # Build project
mvn spring-boot:run  # Run application

# Database (if needed)
mysql -u root -p     # Login to MySQL
```
