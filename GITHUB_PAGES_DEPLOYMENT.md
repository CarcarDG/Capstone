# 🚀 Deploy Frontend to GitHub Pages

## Why GitHub Pages?

✅ **Free** - No cost
✅ **Simple** - Easy setup
✅ **Reliable** - GitHub's infrastructure
✅ **Fast** - CDN included
✅ **Custom Domain** - Supported

---

## 📋 Quick Setup (5 Minutes)

### Step 1: Update Vite Config

Your frontend needs to know it's being served from GitHub Pages.

**Edit `frontend/vite.config.js`:**

```javascript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  // Add this for GitHub Pages
  base: '/Capstone/' // Replace 'Capstone' with your repo name
})
```

### Step 2: Create Deployment Script

**Create `frontend/deploy.sh`:**

```bash
#!/usr/bin/env sh

# Build the project
npm run build

# Navigate into the build output directory
cd dist

# Create .nojekyll to bypass Jekyll processing
echo > .nojekyll

# If deploying to a custom domain
# echo 'www.example.com' > CNAME

git init
git checkout -B main
git add -A
git commit -m 'Deploy to GitHub Pages'

# Push to gh-pages branch
git push -f git@github.com:CarcarDG/Capstone.git main:gh-pages

cd -
```

**For Windows, create `frontend/deploy.bat`:**

```batch
@echo off
echo Building project...
call npm run build

echo Deploying to GitHub Pages...
cd dist

echo. > .nojekyll

git init
git checkout -B main
git add -A
git commit -m "Deploy to GitHub Pages"

git push -f https://github.com/CarcarDG/Capstone.git main:gh-pages

cd ..
echo Deployment complete!
pause
```

### Step 3: Enable GitHub Pages

1. **Go to your GitHub repository**
   - https://github.com/CarcarDG/Capstone

2. **Go to Settings → Pages**

3. **Configure:**
   - Source: Deploy from a branch
   - Branch: `gh-pages`
   - Folder: `/ (root)`

4. **Save**

### Step 4: Deploy

```powershell
cd frontend
npm run build
.\deploy.bat
```

### Step 5: Access Your Site

Your site will be available at:
```
https://carcardg.github.io/Capstone/
```

Wait 1-2 minutes for GitHub to build and deploy.

---

## 🔧 Alternative: GitHub Actions (Automated)

Set up automatic deployment on every push.

**Create `.github/workflows/deploy.yml`:**

```yaml
name: Deploy to GitHub Pages

on:
  push:
    branches:
      - main

jobs:
  deploy:
    runs-on: ubuntu-latest
    
    steps:
      - name: Checkout
        uses: actions/checkout@v3
      
      - name: Setup Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '18'
      
      - name: Install dependencies
        run: |
          cd frontend
          npm install
      
      - name: Build
        run: |
          cd frontend
          npm run build
        env:
          VITE_API_BASE_URL: ${{ secrets.VITE_API_BASE_URL }}
      
      - name: Deploy to GitHub Pages
        uses: peaceiris/actions-gh-pages@v3
        with:
          github_token: ${{ secrets.GITHUB_TOKEN }}
          publish_dir: ./frontend/dist
          cname: your-custom-domain.com # Optional
```

**Add Secret:**
1. Go to Settings → Secrets and variables → Actions
2. Click "New repository secret"
3. Name: `VITE_API_BASE_URL`
4. Value: `https://your-backend.railway.app/api`

Now every time you push to main, it auto-deploys!

---

## 🌐 Custom Domain (Optional)

### Step 1: Add CNAME File

**Create `frontend/public/CNAME`:**
```
www.yourrecipesite.com
```

### Step 2: Configure DNS

At your domain registrar, add:

```
Type: CNAME
Name: www
Value: carcardg.github.io
```

```
Type: A
Name: @
Value: 185.199.108.153
Value: 185.199.109.153
Value: 185.199.110.153
Value: 185.199.111.153
```

### Step 3: Enable in GitHub

Settings → Pages → Custom domain → Enter your domain → Save

---

## ✅ Advantages of GitHub Pages

| Feature | GitHub Pages | Vercel |
|---------|-------------|--------|
| Cost | Free | Free (with limits) |
| Setup | Simple | Can be complex |
| Build Issues | Rare | Common |
| Custom Domain | Yes | Yes |
| SSL | Automatic | Automatic |
| CDN | Yes | Yes |

---

## 🔄 Update Your Site

Whenever you make changes:

```powershell
cd frontend
npm run build
.\deploy.bat
```

Or if using GitHub Actions, just:
```bash
git add .
git commit -m "Update site"
git push
```

---

## 🧪 Test Before Deploying

Always test the build locally:

```powershell
cd frontend
npm run build
npm run preview
```

Visit http://localhost:4173 to see how it will look.

---

## 📝 Environment Variables

Since GitHub Pages is static hosting, you need to set the API URL at build time.

**Option 1: In deploy script**
```batch
set VITE_API_BASE_URL=https://your-backend.railway.app/api
npm run build
```

**Option 2: In .env.production**
```env
VITE_API_BASE_URL=https://your-backend.railway.app/api
```

---

## 🆘 Troubleshooting

### Issue: 404 on page refresh

**Fix:** GitHub Pages needs a 404.html that redirects to index.html

**Create `frontend/public/404.html`:**
```html
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Recipe Platform</title>
  <script>
    sessionStorage.redirect = location.href;
  </script>
  <meta http-equiv="refresh" content="0;URL='/'">
</head>
<body></body>
</html>
```

**Update `frontend/public/index.html`:**
```html
<script>
  (function(){
    var redirect = sessionStorage.redirect;
    delete sessionStorage.redirect;
    if (redirect && redirect != location.href) {
      history.replaceState(null, null, redirect);
    }
  })();
</script>
```

### Issue: Assets not loading

**Fix:** Make sure `base` in vite.config.js matches your repo name.

### Issue: API calls failing

**Fix:** Check VITE_API_BASE_URL is set correctly in .env.production

---

## ✨ Summary

**GitHub Pages is perfect for your frontend because:**
- ✅ No build configuration issues
- ✅ Free forever
- ✅ Simple deployment
- ✅ Works with your existing GitHub repo
- ✅ Automatic SSL
- ✅ Fast CDN

**Your backend stays on Railway** (or wherever you deploy it).

This is actually the **recommended approach** for Vue/React apps with separate backends!
