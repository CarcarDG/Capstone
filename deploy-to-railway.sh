#!/bin/bash

# Recipe Platform - Railway Deployment Script
# This script helps prepare your app for Railway deployment

echo "========================================="
echo "  Recipe Platform - Railway Deployment"
echo "========================================="
echo ""

# Check if git is initialized
if [ ! -d ".git" ]; then
    echo "Initializing git repository..."
    git init
    git add .
    git commit -m "Initial commit for deployment"
fi

# Create Procfile for backend
echo "Creating Procfile for backend..."
cat > backend/Procfile << EOF
web: java -jar target/recipe-platform-1.0.0.jar
EOF

# Create railway.json for configuration
echo "Creating railway.json..."
cat > railway.json << EOF
{
  "\$schema": "https://railway.app/railway.schema.json",
  "build": {
    "builder": "NIXPACKS"
  },
  "deploy": {
    "startCommand": "cd backend && mvn spring-boot:run",
    "restartPolicyType": "ON_FAILURE",
    "restartPolicyMaxRetries": 10
  }
}
EOF

echo ""
echo "✅ Deployment files created!"
echo ""
echo "Next steps:"
echo "1. Push code to GitHub:"
echo "   git remote add origin https://github.com/yourusername/recipe-platform.git"
echo "   git push -u origin main"
echo ""
echo "2. Go to https://railway.app"
echo "3. Click 'New Project' → 'Deploy from GitHub repo'"
echo "4. Select your repository"
echo "5. Add MySQL database: Click 'New' → 'Database' → 'MySQL'"
echo "6. Add environment variables (see HOSTING_FINALIZATION_CHECKLIST.md)"
echo ""
echo "Done! 🚀"
