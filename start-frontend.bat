@echo off
echo ========================================
echo   Recipe Platform - Frontend Startup
echo ========================================
echo.

cd frontend

echo Checking if node_modules exists...
if not exist "node_modules" (
    echo Installing dependencies...
    call npm install
) else (
    echo Dependencies already installed.
)

echo.
echo Starting development server...
echo.
echo The app will open at: http://localhost:5173
echo.
echo Login credentials:
echo   Admin: admin / admin123
echo   User:  john / user123
echo.
echo Press Ctrl+C to stop the server
echo.

call npm run dev

pause
