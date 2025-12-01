@echo off
echo ========================================
echo Testing API Data Connection
echo ========================================
echo.

echo [1/3] Testing Health Check...
curl -s https://recipe-backend-npce.onrender.com/health
echo.
echo.

echo [2/3] Testing Categories API...
curl -s https://recipe-backend-npce.onrender.com/api/categories
echo.
echo.

echo [3/3] Testing Recipes API...
curl -s https://recipe-backend-npce.onrender.com/api/recipes
echo.
echo.

echo ========================================
echo Test Complete
echo ========================================
echo.
echo If you see JSON data above (not empty arrays []),
echo your database connection is working!
echo.
echo If you see empty arrays [], you need to:
echo 1. Import data to Railway database
echo 2. Check database connection settings
echo.
pause
