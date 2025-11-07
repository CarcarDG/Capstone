@echo off
echo ========================================
echo   Recipe Platform - Development Mode
echo ========================================
echo.

echo Checking prerequisites...
echo.

where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Java not found! Please install JDK 21.
    pause
    exit /b 1
)

where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven not found! Please install Maven.
    pause
    exit /b 1
)

where node >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Node.js not found! Please install Node.js 22.
    pause
    exit /b 1
)

echo [OK] All prerequisites found!
echo.

echo Starting Backend Server...
start "Recipe Platform - Backend" cmd /k "cd /d %~dp0backend && mvn spring-boot:run"

echo Waiting for backend to initialize...
timeout /t 15 /nobreak >nul

echo Starting Frontend Server...
start "Recipe Platform - Frontend" cmd /k "cd /d %~dp0frontend && npm run dev"

echo.
echo ========================================
echo   Servers Starting...
echo ========================================
echo.
echo Backend API:  http://localhost:8080/api
echo Frontend:     http://localhost:5173
echo.
echo Login Credentials:
echo   Admin: admin / admin123
echo   User:  ccc / user123
echo.
echo Press any key to open browser...
pause >nul

start http://localhost:5173

echo.
echo To stop servers, close their terminal windows.
echo.
