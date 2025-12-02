@echo off
echo ========================================
echo Deploy: Fix Recipes Loading
echo ========================================
echo.

echo [1/4] Adding changes to Git...
git add frontend/src/views/user/Recipes.vue
git add deploy-fix-recipes-load.bat
echo.

echo [2/4] Committing changes...
git commit -m "fix: Add onMounted hook to load recipes on page load"
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
echo 2. Clear browser cache (Ctrl+Shift+Delete)
echo 3. Refresh the recipes page
echo 4. New recipes should now appear!
echo.
echo Fixed issue:
echo - Added onMounted hook to fetch recipes on page load
echo - Added loading state indicator
echo.
pause
