# 🔧 Vercel Deployment Fix Guide

**Issue:** Deployments failing on Vercel  
**Solution:** Updated configuration files

---

## ✅ What I Fixed

### 1. Created `vercel.json` Configuration
Added proper build settings for your monorepo structure:
- Build command points to frontend folder
- Output directory set to `frontend/dist`
- SPA routing configured (all routes go to index.html)

### 2. Updated `frontend/vite.config.js`
- Added Vercel environment detection
- Uses root path `/` for Vercel (instead of `/Capstone/`)
- Keeps `/Capstone/` for GitHub Pages
- Added explicit build configuration

---

## 🚀 How to Deploy to Vercel (Fixed)

### Method 1: Push to GitHub (Automatic)

1. **Commit the new configuration files:**
   ```powershell
   git add vercel.json frontend/vite.config.js
   git commit -m "Fix Vercel deployment configuration"
   git push origin main
   ```

2. **Vercel will automatically redeploy** with the correct settings

3. **Wait 2-3 minutes** and check your deployment

---

### Method 2: Redeploy from Vercel Dashboard

1. Go to your Vercel dashboard: https://vercel.com/carcardg/capstone
2. Click on the failed deployment
3. Click **"Redeploy"** button
4. The new configuration will be used automatically

---

### Method 3: Deploy via CLI

1. **Install Vercel CLI** (if not installed):
   ```powershell
   npm install -g vercel
   ```

2. **Login to Vercel:**
   ```powershell
   vercel login
   ```

3. **Deploy:**
   ```powershell
   vercel --prod
   ```

---

## 🔍 Vercel Project Settings to Check

Go to your Vercel project settings and verify:

### Build & Development Settings

1. **Framework Preset:** Other (or Vite)
2. **Root Directory:** `./` (leave empty or use root)
3. **Build Command:** `cd frontend && npm install && npm run build`
4. **Output Directory:** `frontend/dist`
5. **Install Command:** `cd frontend && npm install`

### To update these settings:
1. Go to: https://vercel.com/carcardg/capstone/settings
2. Navigate to "Build & Development Settings"
3. Update the values above
4. Click "Save"
5. Redeploy

---

## 🐛 Common Vercel Deployment Errors & Fixes

### Error: "Command failed with exit code 1"
**Cause:** Build command can't find files  
**Fix:** ✅ Already fixed with `vercel.json`

### Error: "404 on page refresh"
**Cause:** SPA routing not configured  
**Fix:** ✅ Already fixed with rewrites in `vercel.json`

### Error: "Module not found"
**Cause:** Dependencies not installed  
**Fix:** ✅ Already fixed with proper install command

### Error: "Build directory not found"
**Cause:** Wrong output directory  
**Fix:** ✅ Already fixed with correct outputDirectory

---

## 📋 Deployment Checklist

Before deploying, make sure:

- [x] `vercel.json` exists in root directory
- [x] `frontend/vite.config.js` updated with Vercel support
- [ ] All changes committed to Git
- [ ] Pushed to GitHub
- [ ] Vercel project settings are correct
- [ ] No syntax errors in code

---

## 🎯 Expected Vercel URLs

After successful deployment:

- **Production:** `https://capstone-[random].vercel.app`
- **Custom Domain:** (if configured) `https://your-domain.com`

You can set a custom Vercel subdomain in project settings.

---

## 🔄 Alternative: Use Netlify Instead

If Vercel continues to have issues, Netlify is easier:

### Quick Netlify Deploy:

1. **Build locally:**
   ```powershell
   cd frontend
   npm run build
   ```

2. **Deploy:**
   - Go to: https://app.netlify.com/drop
   - Drag `frontend/dist` folder
   - Done!

### Or connect to GitHub:

1. Go to: https://app.netlify.com
2. "Add new site" → "Import an existing project"
3. Connect to GitHub: `CarcarDG/Capstone`
4. Settings:
   - **Base directory:** `frontend`
   - **Build command:** `npm run build`
   - **Publish directory:** `frontend/dist`
5. Deploy!

---

## 🔍 Debugging Failed Deployments

### Check Vercel Build Logs:

1. Go to your deployment: https://vercel.com/carcardg/capstone
2. Click on the failed deployment
3. Click "View Build Logs"
4. Look for the error message

### Common error patterns:

**"Cannot find module"**
- Missing dependency in package.json
- Run `npm install` locally to verify

**"Command not found"**
- Wrong build command
- Check vercel.json configuration

**"Build exceeded maximum duration"**
- Build taking too long
- Check for infinite loops or large dependencies

---

## ✅ Test Your Deployment

Once deployed successfully, test:

1. **Home page loads:** `https://your-url.vercel.app/`
2. **Login page:** `https://your-url.vercel.app/login`
3. **Direct URL access:** `https://your-url.vercel.app/recipes`
4. **Page refresh works** (no 404 errors)
5. **All routes work** (navigation)

**Test Credentials:**
- User: `user@example.com` / `password123`
- Admin: `admin@example.com` / `admin123`

---

## 🎨 Vercel vs GitHub Pages vs Netlify

| Feature | Vercel | GitHub Pages | Netlify |
|---------|--------|--------------|---------|
| **Setup** | Medium | Easy | Easiest |
| **Speed** | Fast | Medium | Fast |
| **Custom Domain** | Free | Free | Free |
| **Build Time** | Fast | Slow | Fast |
| **Auto Deploy** | Yes | Yes | Yes |
| **Serverless** | Yes | No | Yes |

**My Recommendation:** 
- Use **Netlify** for simplest deployment
- Use **Vercel** for best performance
- Use **GitHub Pages** for free hosting with your repo

---

## 🚀 Next Steps

1. **Commit the fixes:**
   ```powershell
   git add vercel.json frontend/vite.config.js
   git commit -m "Fix Vercel deployment"
   git push origin main
   ```

2. **Wait for automatic deployment** or click "Redeploy" in Vercel

3. **If still failing:** Check the build logs and share the error message

4. **Alternative:** Try Netlify deployment (see above)

---

## 📞 Still Having Issues?

If deployment still fails:

1. **Share the build log error** from Vercel
2. **Try Netlify** as an alternative (easier setup)
3. **Check Vercel project settings** match the configuration above

---

## ✅ Quick Fix Summary

**Files Updated:**
- ✅ Created `vercel.json` with correct build settings
- ✅ Updated `frontend/vite.config.js` for Vercel compatibility

**Next Action:**
```powershell
git add vercel.json frontend/vite.config.js
git commit -m "Fix Vercel deployment configuration"
git push origin main
```

**Then:** Wait for Vercel to auto-deploy or click "Redeploy" in dashboard!
