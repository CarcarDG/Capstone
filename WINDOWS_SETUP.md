# Windows Setup Guide

## Step-by-Step Installation

### 1. Install Prerequisites

#### Install JDK 21

1. Download JDK 21 from: https://www.oracle.com/java/technologies/downloads/#java21
2. Run the installer
3. Verify installation:
```powershell
java -version
```

#### Install Maven

1. Download Maven from: https://maven.apache.org/download.cgi
   - Download the Binary zip archive (e.g., `apache-maven-3.9.5-bin.zip`)

2. Extract to `C:\Program Files\Apache\maven`

3. Add Maven to PATH:
   - Open System Properties → Environment Variables
   - Under System Variables, find `Path` and click Edit
   - Add: `C:\Program Files\Apache\maven\bin`
   - Click OK

4. Verify installation:
```powershell
mvn -version
```

#### Install Node.js 22

1. Download from: https://nodejs.org/
2. Run the installer (includes npm)
3. Verify installation:
```powershell
node -v
npm -v
```

#### Install MySQL

1. Download MySQL Installer from: https://dev.mysql.com/downloads/installer/
2. Run installer and choose "Developer Default"
3. Set root password during installation
4. Verify installation:
```powershell
mysql --version
```

### 2. Setup Database

#### Option A: Using Batch File (Easiest)

```powershell
# Run the batch file
.\setup-database.bat
```

When prompted, enter your MySQL root password.

#### Option B: Manual Setup

```powershell
# Login to MySQL
mysql -u root -p

# In MySQL prompt, run:
source D:/carso/Documents/Capstone/database/schema.sql
source D:/carso/Documents/Capstone/database/sample_data.sql

# Exit MySQL
exit
```

#### Option C: Using MySQL Workbench (GUI)

1. Open MySQL Workbench
2. Connect to your local MySQL server
3. File → Open SQL Script → Select `database/schema.sql`
4. Execute the script (lightning bolt icon)
5. Repeat for `database/sample_data.sql`

### 3. Setup Backend

```powershell
# Navigate to backend directory
cd D:\carso\Documents\Capstone\backend

# Build the project (first time)
mvn clean install

# Run the application
mvn spring-boot:run
```

**Note:** First build will take several minutes as Maven downloads dependencies.

The backend will start on: http://localhost:8080/api

### 4. Setup Frontend

Open a **NEW** PowerShell window:

```powershell
# Navigate to frontend directory
cd D:\carso\Documents\Capstone\frontend

# Install dependencies (first time only)
npm install

# Start development server
npm run dev
```

The frontend will start on: http://localhost:5173

### 5. Access the Application

Open your browser and go to: http://localhost:5173

**Login Credentials:**
- Admin: `admin` / `admin123`
- User: `ccc` / `user123`

## Troubleshooting

### Maven Command Not Found

If `mvn` command is not recognized after installation:

1. Close and reopen PowerShell
2. Verify PATH is set correctly:
```powershell
$env:Path
```
3. Should contain Maven bin directory

### MySQL Connection Failed

Edit `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/recipe_platform?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: YOUR_MYSQL_ROOT_PASSWORD  # Change this!
```

### Port Already in Use

**Backend (8080):**
```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Kill process (replace PID with actual process ID)
taskkill /PID <PID> /F
```

**Frontend (5173):**
```powershell
# Find process using port 5173
netstat -ano | findstr :5173

# Kill process
taskkill /PID <PID> /F
```

### Node Modules Issues

```powershell
cd frontend
rm -r node_modules
rm package-lock.json
npm install
```

## Quick Start Script

Create `start-dev.bat` in project root:

```batch
@echo off
echo Starting Recipe Platform...

start "Backend" cmd /k "cd backend && mvn spring-boot:run"
timeout /t 10
start "Frontend" cmd /k "cd frontend && npm run dev"

echo.
echo Backend: http://localhost:8080/api
echo Frontend: http://localhost:5173
echo.
pause
```

Then just run:
```powershell
.\start-dev.bat
```

## Alternative: Using Visual Studio Code

1. Install VS Code: https://code.visualstudio.com/
2. Install extensions:
   - Java Extension Pack
   - Vue Language Features (Volar)
   - Spring Boot Extension Pack

3. Open project folder in VS Code
4. Use integrated terminal for commands

## Verification Checklist

- [ ] JDK 21 installed (`java -version`)
- [ ] Maven installed (`mvn -version`)
- [ ] Node.js 22 installed (`node -v`)
- [ ] MySQL installed and running
- [ ] Database created and populated
- [ ] Backend starts without errors
- [ ] Frontend starts without errors
- [ ] Can login to application

## Next Steps After Setup

1. Test login with admin account
2. Explore admin dashboard
3. Test user account features
4. Review code structure
5. Start implementing additional features

## Common Windows-Specific Issues

### Execution Policy Error

If you get execution policy errors:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Long Path Issues

Enable long paths in Windows:
```powershell
# Run as Administrator
New-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Control\FileSystem" -Name "LongPathsEnabled" -Value 1 -PropertyType DWORD -Force
```

### Firewall Blocking

If firewall blocks the application:
1. Windows Security → Firewall & network protection
2. Allow an app through firewall
3. Add Java and Node.js

## Getting Help

If you encounter issues:
1. Check error messages carefully
2. Verify all prerequisites are installed
3. Check MySQL is running: `services.msc` → MySQL
4. Review logs in console output
5. Check browser console (F12) for frontend errors
