#!/bin/bash

# Recipe Platform - Vercel Deployment Script
# This script helps prepare your frontend for Vercel deployment

echo "========================================="
echo "  Recipe Platform - Vercel Deployment"
echo "========================================="
echo ""

# Create vercel.json
echo "Creating vercel.json..."
cat > frontend/vercel.json << EOF
{
  "version": 2,
  "builds": [
    {
      "src": "package.json",
      "use": "@vercel/static-build",
      "config": {
        "distDir": "dist"
      }
    }
  ],
  "routes": [
    {
      "src": "/(.*)",
      "dest": "/index.html"
    }
  ]
}
EOF

# Update package.json with vercel build script
echo "Updating package.json..."
cd frontend
npm pkg set scripts.vercel-build="npm run build"
cd ..

echo ""
echo "✅ Vercel configuration created!"
echo ""
echo "Next steps:"
echo "1. Push code to GitHub (if not already done)"
echo "2. Go to https://vercel.com"
echo "3. Click 'New Project'"
echo "4. Import your GitHub repository"
echo "5. Configure:"
echo "   - Framework: Vite"
echo "   - Root Directory: frontend"
echo "   - Build Command: npm run build"
echo "   - Output Directory: dist"
echo "6. Add environment variable:"
echo "   VITE_API_BASE_URL=https://your-backend-url.railway.app/api"
echo "7. Click 'Deploy'"
echo ""
echo "Done! 🚀"
