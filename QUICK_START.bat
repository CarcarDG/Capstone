@echo off
echo ========================================
echo   Recipe Platform - Quick Start
echo ========================================
echo.

echo Step 1: Checking MySQL...
net start MySQL80 >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] MySQL is running
) else (
    echo [WARNING] MySQL might not be running
    echo Please start MySQL manually
)
echo.

echo Step 2: Starting Backend...
echo Opening new terminal for backend...
start cmd /k "cd backend && echo Starting Backend... && mvn spring-boot:run"
timeout /t 3 >nul
echo.

echo Step 3: Starting Frontend...
echo Opening new terminal for frontend...
start cmd /k "cd frontend && echo Starting Frontend... && npm run dev"
echo.

echo ========================================
echo   Services Starting...
echo ========================================
echo.
echo Backend will be available at: http://localhost:8080
echo Frontend will be available at: http://localhost:5173
echo.
echo Login with:
echo   Admin: admin / admin123
echo   User:  john / user123
echo.
echo Press any key to exit this window...
pause >nul
