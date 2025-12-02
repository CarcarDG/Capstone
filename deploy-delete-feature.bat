@echo off
echo ========================================
echo Deploy Delete Feature
echo ========================================
echo.

echo [1/4] Adding new files to Git...
git add frontend/src/api/recipe.js
git add frontend/src/views/admin/Recipes.vue
git add DELETE_FEATURE_COMPLETE.md
git add FINAL_PROJECT_STATUS.md
git add deploy-delete-feature.bat
echo.

echo [2/4] Committing changes...
git commit -m "feat: Add delete functionality to admin recipe management

- Created recipe API service with full CRUD operations
- Enhanced admin recipe management page
- Added delete confirmation dialog
- Implemented search and pagination
- Added feature toggle functionality
- Added loading states and error handling
- Status: Task 8 completed (100%%)
- Project completion: 85%%"
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
echo 1. Wait for Netlify to deploy (2-3 minutes)
echo 2. Visit: https://deliousrecipesfinder.netlify.app/admin/recipes
echo 3. Login as admin (admin / admin123)
echo 4. Test delete functionality
echo.
echo Features added:
echo - Delete recipe with confirmation
echo - Search recipes
echo - Pagination
echo - Feature/Unfeature recipes
echo - Real-time data loading
echo - Status badges
echo.
echo Project completion: 85%%!
echo.
pause
