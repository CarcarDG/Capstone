# Recipe Platform - Development Startup Script

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Recipe Platform - Development Mode" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check prerequisites
Write-Host "Checking prerequisites..." -ForegroundColor Yellow
Write-Host ""

$javaInstalled = Get-Command java -ErrorAction SilentlyContinue
$mavenInstalled = Get-Command mvn -ErrorAction SilentlyContinue
$nodeInstalled = Get-Command node -ErrorAction SilentlyContinue

if (-not $javaInstalled) {
    Write-Host "[ERROR] Java not found! Please install JDK 21." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

if (-not $mavenInstalled) {
    Write-Host "[ERROR] Maven not found! Please install Maven." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

if (-not $nodeInstalled) {
    Write-Host "[ERROR] Node.js not found! Please install Node.js 22." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "[OK] All prerequisites found!" -ForegroundColor Green
Write-Host ""

# Start Backend
Write-Host "Starting Backend Server..." -ForegroundColor Yellow
$backendPath = Join-Path $PSScriptRoot "backend"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$backendPath'; Write-Host 'Backend Server' -ForegroundColor Green; mvn spring-boot:run"

# Wait for backend to initialize
Write-Host "Waiting for backend to initialize..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

# Start Frontend
Write-Host "Starting Frontend Server..." -ForegroundColor Yellow
$frontendPath = Join-Path $PSScriptRoot "frontend"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$frontendPath'; Write-Host 'Frontend Server' -ForegroundColor Green; npm run dev"

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Servers Starting..." -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Backend API:  http://localhost:8080/api" -ForegroundColor Green
Write-Host "Frontend:     http://localhost:5173" -ForegroundColor Green
Write-Host ""
Write-Host "Login Credentials:" -ForegroundColor Yellow
Write-Host "  Admin: admin / admin123"
Write-Host "  User:  ccc / user123"
Write-Host ""

# Wait a bit more for frontend to start
Start-Sleep -Seconds 5

# Open browser
Write-Host "Opening browser..." -ForegroundColor Yellow
Start-Process "http://localhost:5173"

Write-Host ""
Write-Host "To stop servers, close their PowerShell windows." -ForegroundColor Cyan
Write-Host ""
