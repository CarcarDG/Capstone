@echo off
echo ========================================
echo Deploy Database Integration
echo ========================================
echo.

echo [1/4] Adding new files to Git...
git add backend/src/main/java/com/recipe/entity/Category.java
git add backend/src/main/java/com/recipe/entity/Recipe.java
git add backend/src/main/java/com/recipe/repository/CategoryRepository.java
git add backend/src/main/java/com/recipe/repository/RecipeRepository.java
git add backend/src/main/java/com/recipe/service/CategoryService.java
git add backend/src/main/java/com/recipe/service/RecipeService.java
git add backend/src/main/java/com/recipe/controller/CategoryController.java
git add backend/src/main/java/com/recipe/controller/RecipeController.java
git add deploy-database-integration.bat
echo.

echo [2/4] Committing changes...
git commit -m "feat: Connect backend to Railway database - Add entities, repositories, and services"
echo.

echo [3/4] Pushing to GitHub...
git push origin main
echo.

echo [4/4] Deployment initiated!
echo.

echo ========================================
echo Success!
echo ========================================
echo.
echo Next steps:
echo 1. Wait 3-5 minutes for Render to deploy
echo 2. Check Render logs: https://dashboard.render.com/
echo 3. Test API endpoints:
echo    - https://recipe-backend-npce.onrender.com/api/categories
echo    - https://recipe-backend-npce.onrender.com/api/recipes
echo.
echo Note: Make sure Railway database has data!
echo Run database/sample_data.sql if needed.
echo.
pause
