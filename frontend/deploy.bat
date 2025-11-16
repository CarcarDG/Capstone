@echo off
echo ========================================
echo   Deploying to GitHub Pages
echo ========================================
echo.

echo Building project...
call npm run build

if errorlevel 1 (
    echo Build failed!
    pause
    exit /b 1
)

echo.
echo Deploying to GitHub Pages...
cd dist

echo. > .nojekyll

git init
git checkout -B main
git add -A
git commit -m "Deploy to GitHub Pages"

echo Pushing to gh-pages branch...
git push -f https://github.com/CarcarDG/Capstone.git main:gh-pages

cd ..

echo.
echo ========================================
echo   Deployment Complete!
echo ========================================
echo.
echo Your site will be available at:
echo https://carcardg.github.io/Capstone/
echo.
echo Wait 1-2 minutes for GitHub to process the deployment.
echo.
pause
