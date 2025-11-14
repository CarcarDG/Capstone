# Vercel Deployment Fix

## Issue
Build failing with module resolution error.

## Solution

### Option 1: Correct Root Directory (Recommended)

1. **Go to Vercel Project Settings**
   - Click on your project
   - Go to "Settings" tab
   - Find "Root Directory" section

2. **Set Root Directory**
   ```
   Root Directory: frontend
   ```

3. **Configure Build Settings**
   ```
   Framework Preset: Vite
   Build Command: npm run build
   Output Directory: dist
   Install Command: npm install
   ```

4. **Environment Variables**
   ```
   VITE_API_BASE_URL=https://your-backend-url.railway.app/api
   ```

5. **Redeploy**
   - Click "Deployments" tab
   - Click "..." on latest deployment
   - Click "Redeploy"

### Option 2: Build from Project Root

If you want to keep the root directory as the project root:

1. **Update Build Command**
   ```
   Build Command: cd frontend && npm install && npm run build
   Output Directory: frontend/dist
   ```

2. **Or use the vercel.json** (already created in project root)

### Option 3: Move Frontend to Root (Not Recommended)

Only if you want frontend-only deployment:
```bash
# Move all frontend files to root
mv frontend/* .
mv frontend/.* .
rmdir frontend
```

## Recommended: Option 1

**Steps:**
1. Delete current Vercel project
2. Create new project
3. Import from GitHub
4. **IMPORTANT:** Set "Root Directory" to `frontend`
5. Set Framework to "Vite"
6. Add environment variable
7. Deploy

## Verify Deployment

After successful deployment:
1. Visit your Vercel URL
2. Check browser console (F12)
3. Verify API calls are going to correct backend URL
4. Test login with: admin / admin123

## Common Issues

### Issue: "Cannot find module"
**Fix:** Make sure Root Directory is set to `frontend`

### Issue: "Build command failed"
**Fix:** Check that package.json exists in frontend folder

### Issue: "404 on page refresh"
**Fix:** Vercel should auto-configure SPA routing, but if not, the vercel.json handles it

### Issue: "API calls failing"
**Fix:** Check VITE_API_BASE_URL environment variable is set correctly

## Test Locally First

Before deploying, test the build locally:

```powershell
cd frontend
npm install
npm run build
npm run preview
```

If this works locally, it should work on Vercel.
