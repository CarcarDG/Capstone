@echo off
echo ========================================
echo Testing Backend Build
echo ========================================
echo.

echo [1/2] Navigating to backend directory...
cd backend
echo.

echo [2/2] Running Maven compile (skip tests)...
mvn clean compile -DskipTests
echo.

if %ERRORLEVEL% EQU 0 (
    echo ========================================
    echo Build Successful! ✅
    echo ========================================
    echo.
    echo All entities, repositories, and services compiled successfully.
    echo You can now deploy to Render.
    echo.
    echo Run: deploy-database-integration.bat
) else (
    echo ========================================
    echo Build Failed! ❌
    echo ========================================
    echo.
    echo Please check the error messages above.
)

cd ..
pause
