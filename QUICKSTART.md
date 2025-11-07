# Quick Start Guide - Windows

## Prerequisites Installation

**First time setup? Follow these steps:**

1. **Install all prerequisites** (takes ~25 minutes):
   - See [INSTALL_PREREQUISITES.md](INSTALL_PREREQUISITES.md) for detailed instructions
   - Download and install: JDK 21, Maven, Node.js 22, MySQL

2. **Verify installations** in PowerShell:
   ```powershell
   java -version
   mvn -version
   node -v
   mysql --version
   ```

## Database Setup

### Option 1: Automated (Recommended)

```powershell
.\setup-database.bat
```

Enter your MySQL root password when prompted.

### Option 2: Manual

```powershell
mysql -u root -p
```

Then in MySQL:
```sql
source D:/carso/Documents/Capstone/database/schema.sql
source D:/carso/Documents/Capstone/database/sample_data.sql
exit
```

### Option 3: MySQL Workbench (GUI)

1. Open MySQL Workbench
2. Connect to localhost
3. File → Open SQL Script → `database/schema.sql`
4. Execute (⚡ icon)
5. Repeat for `database/sample_data.sql`

## Configure Backend

Edit `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    password: YOUR_MYSQL_ROOT_PASSWORD  # Change this line!
```

## Start Application

### Option 1: Automated Startup (Easiest)

```powershell
.\start-dev.bat
```

This will:
- Check prerequisites
- Start backend server
- Start frontend server
- Open browser automatically

### Option 2: Manual Startup

**Terminal 1 - Backend:**
```powershell
cd backend
mvn spring-boot:run
```

**Terminal 2 - Frontend:**
```powershell
cd frontend
npm install  # First time only
npm run dev
```

## Access Application

Open browser: **http://localhost:5173**

### Login Credentials

**Administrator:**
- Username: `admin`
- Password: `admin123`
- Access: Admin Dashboard

**Regular Users:**
- Username: `ccc` / Password: `user123`
- Username: `nana` / Password: `user123`
- Access: User Homepage

## What to Test

### As Admin (admin/admin123)

1. ✅ Login to admin dashboard
2. ✅ View statistics on homepage
3. ✅ Manage users (view, edit, delete)
4. ✅ Manage recipes
5. ✅ Manage food notes
6. ✅ Manage announcements

### As User (ccc/user123)

1. ✅ Login to user homepage
2. ✅ View carousel banner
3. ✅ Browse announcements
4. ✅ View recipe cards
5. ✅ View food notes
6. ✅ Search functionality
7. ✅ User profile dropdown

## Troubleshooting

### Backend won't start

**Error: "mvn: command not found"**
- Maven not installed or not in PATH
- See [INSTALL_PREREQUISITES.md](INSTALL_PREREQUISITES.md)

**Error: "Could not connect to database"**
- Check MySQL is running: `Get-Service MySQL*`
- Verify password in `application.yml`
- Ensure database exists: `SHOW DATABASES;` in MySQL

**Error: "Port 8080 already in use"**
```powershell
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Frontend won't start

**Error: "npm: command not found"**
- Node.js not installed
- See [INSTALL_PREREQUISITES.md](INSTALL_PREREQUISITES.md)

**Error: "Cannot find module"**
```powershell
cd frontend
rm -r node_modules
rm package-lock.json
npm install
```

**Error: "Port 5173 already in use"**
```powershell
netstat -ano | findstr :5173
taskkill /PID <PID> /F
```

### Database issues

**Error: "Access denied for user 'root'"**
- Wrong password in `application.yml`
- Reset MySQL root password if forgotten

**Error: "Unknown database 'recipe_platform'"**
- Database not created
- Run `setup-database.bat` again

### Login not working

1. Check backend is running (http://localhost:8080/api)
2. Check browser console (F12) for errors
3. Clear browser cache and localStorage
4. Verify credentials are correct

## Project Structure

```
Capstone/
├── backend/              # Spring Boot API (Port 8080)
├── frontend/             # Vue.js App (Port 5173)
├── database/             # SQL scripts
├── setup-database.bat    # Database setup script
├── start-dev.bat         # Start both servers
└── QUICKSTART.md         # This file
```

## Development Workflow

1. **Start servers**: `.\start-dev.bat`
2. **Make changes**: Edit code in VS Code or your IDE
3. **Auto-reload**: 
   - Backend: Spring DevTools auto-reloads
   - Frontend: Vite HMR updates instantly
4. **Test changes**: Refresh browser
5. **Stop servers**: Close terminal windows

## Useful Commands

```powershell
# Backend
cd backend
mvn clean install        # Build project
mvn spring-boot:run      # Run server
mvn test                 # Run tests

# Frontend
cd frontend
npm install              # Install dependencies
npm run dev              # Development server
npm run build            # Production build
npm run preview          # Preview production build

# Database
mysql -u root -p         # Login to MySQL
```

## Next Steps

After successful setup:

1. ✅ Explore the codebase
2. ✅ Review [SETUP.md](SETUP.md) for detailed documentation
3. ✅ Check [backend/README.md](backend/README.md) for API docs
4. ✅ Check [frontend/README.md](frontend/README.md) for frontend docs
5. ✅ Start implementing additional features

## Getting Help

1. **Check error messages** carefully
2. **Review logs** in terminal output
3. **Check browser console** (F12) for frontend errors
4. **Verify prerequisites** are installed correctly
5. **Restart servers** if something seems stuck

## Common First-Time Issues

### Issue: Maven downloads many dependencies

**This is normal!** First run takes 5-10 minutes to download all dependencies.

### Issue: npm install takes long

**This is normal!** First run takes 2-5 minutes to download packages.

### Issue: Browser shows "Cannot connect"

**Wait a moment!** Servers need 10-30 seconds to fully start.

### Issue: Changes not reflecting

1. Check file is saved
2. Check terminal for errors
3. Hard refresh browser (Ctrl+Shift+R)
4. Restart development server

## Success Indicators

✅ Backend running: See "Started RecipePlatformApplication" in terminal
✅ Frontend running: See "Local: http://localhost:5173" in terminal
✅ Database connected: No connection errors in backend log
✅ Login works: Can access admin or user dashboard

## Estimated Time

- **First-time setup**: 30-40 minutes (including installations)
- **Subsequent starts**: 30-60 seconds
- **Database setup**: 1-2 minutes (one-time)

## Support

For detailed documentation:
- [WINDOWS_SETUP.md](WINDOWS_SETUP.md) - Windows-specific guide
- [SETUP.md](SETUP.md) - Complete setup documentation
- [INSTALL_PREREQUISITES.md](INSTALL_PREREQUISITES.md) - Installation guide

Happy coding! 🚀
