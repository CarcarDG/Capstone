# 🚀 Step-by-Step Deployment Guide

**Your Repository:** https://github.com/CarcarDG/Capstone  
**Your Live URL:** https://carcardg.github.io/Capstone/

---

## 📋 Prerequisites Check

Before deploying, you need:

### ✅ 1. Git Installed

Check if Git is installed:
```powershell
git --version
```

**If not installed:**
1. Download: https://git-scm.com/download/win
2. Install with default settings
3. Restart your terminal/IDE
4. Verify: `git --version`

### ✅ 2. GitHub Account Connected

Check if you're logged in:
```powershell
git config user.name
git config user.email
```

**If not configured:**
```powershell
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

---

## 🎯 Method 1: Automated Deploy (Easiest)

### Step 1: Commit Your Changes

```powershell
git add .
git commit -m "Ready for deployment"
git push origin main
```

**If this asks for credentials:**
- Username: `CarcarDG`
- Password: Use a Personal Access Token (see below)

### Step 2: Run Deploy Script

```powershell
cd frontend
.\deploy.bat
```

### Step 3: Enable GitHub Pages

1. Visit: https://github.com/CarcarDG/Capstone/settings/pages
2. Under "Build and deployment":
   - **Source:** Deploy from a branch
   - **Branch:** `gh-pages` → `/ (root)`
3. Click **Save**
4. Wait 2-3 minutes

### Step 4: Visit Your Site

**https://carcardg.github.io/Capstone/**

---

## 🔑 Creating a Personal Access Token

If Git asks for a password:

1. Go to: https://github.com/settings/tokens
2. Click **"Generate new token"** → **"Generate new token (classic)"**
3. Settings:
   - **Note:** "Capstone Deployment"
   - **Expiration:** 90 days (or custom)
   - **Scopes:** Check `repo` (all sub-options)
4. Click **"Generate token"**
5. **COPY THE TOKEN** (you won't see it again!)
6. Use this token as your password when Git asks

---

## 🌐 Method 2: GitHub Desktop (No Command Line)

### Step 1: Install GitHub Desktop

1. Download: https://desktop.github.com/
2. Install and sign in with your GitHub account

### Step 2: Open Your Repository

1. File → Add Local Repository
2. Select: `D:\carso\Documents\Capstone`
3. Click "Add Repository"

### Step 3: Commit and Push

1. You'll see all your changes listed
2. Add commit message: "Ready for deployment"
3. Click **"Commit to main"**
4. Click **"Push origin"**

### Step 4: Build and Deploy

Open PowerShell in your project:
```powershell
cd frontend
npm run build
```

Then manually upload the `dist` folder to GitHub Pages (see Method 3)

---

## 📤 Method 3: Manual Upload (No Git Required)

### Step 1: Build Your Project

```powershell
cd frontend
npm run build
```

This creates a `frontend/dist` folder with your website.

### Step 2: Upload to Netlify (Easiest!)

1. Go to: https://app.netlify.com/drop
2. Drag the `frontend/dist` folder onto the page
3. Done! You get an instant live URL

**To get a better URL:**
1. Sign up/login to Netlify
2. Go to Site settings → Domain management
3. Change site name to: `capstone-recipe-platform`
4. Your URL: `https://capstone-recipe-platform.netlify.app`

### Step 3: Or Upload to GitHub Pages Manually

1. Go to: https://github.com/CarcarDG/Capstone
2. Create a new branch called `gh-pages`
3. Upload all files from `frontend/dist` to this branch
4. Enable GitHub Pages (point to `gh-pages` branch)

---

## ⚡ Method 4: Use Vercel (Fastest!)

### Step 1: Install Vercel

```powershell
npm install -g vercel
```

### Step 2: Deploy

```powershell
cd frontend
vercel
```

Follow the prompts:
- **Set up and deploy?** Yes
- **Which scope?** Your account
- **Link to existing project?** No
- **Project name?** capstone-recipe-platform
- **Directory?** `./`
- **Override settings?** No

You'll get a live URL instantly!

---

## 🎨 Recommended: Use Netlify (Simplest)

This is the easiest method with no Git required:

### Quick Steps:

1. **Build:**
   ```powershell
   cd frontend
   npm run build
   ```

2. **Deploy:**
   - Go to: https://app.netlify.com/drop
   - Drag `frontend/dist` folder
   - Done!

3. **Get Better URL:**
   - Sign up at netlify.com
   - Site settings → Change site name
   - Use: `capstone-recipe-platform`

**Your URL:** `https://capstone-recipe-platform.netlify.app`

### Advantages:
- ✅ No Git required
- ✅ Instant deployment
- ✅ Automatic HTTPS
- ✅ Free forever
- ✅ Easy updates (just drag new dist folder)

---

## 🔄 Updating Your Live Site

### If using GitHub Pages:
```powershell
git add .
git commit -m "Update"
git push origin main
cd frontend
.\deploy.bat
```

### If using Netlify:
```powershell
cd frontend
npm run build
```
Then drag the new `dist` folder to Netlify

### If using Vercel:
```powershell
cd frontend
vercel --prod
```

---

## 📱 Test Your Live Site

Once deployed, test these pages:

- ✅ Home: `your-url/`
- ✅ Login: `your-url/login`
- ✅ Recipes: `your-url/recipes`
- ✅ Admin: `your-url/admin`

**Test Credentials:**
- User: `user@example.com` / `password123`
- Admin: `admin@example.com` / `admin123`

---

## 🐛 Common Issues

### Issue: "Git not found"
**Solution:** Install Git from https://git-scm.com/download/win

### Issue: "Authentication failed"
**Solution:** Use a Personal Access Token instead of password

### Issue: "404 on refresh"
**Solution:** Already fixed in your vite.config.js!

### Issue: "Blank page"
**Solution:** Check browser console for errors. Make sure base path is correct.

---

## 🎯 My Recommendation for You

Since Git might not be set up yet, I recommend:

### **Use Netlify Drop (5 minutes):**

1. Open PowerShell in your project
2. Run:
   ```powershell
   cd frontend
   npm run build
   ```
3. Go to: https://app.netlify.com/drop
4. Drag the `frontend/dist` folder
5. Done! Share your live URL

**This is the fastest way to get your site live right now!**

---

## 📞 Need Help?

Choose your preferred method and let me know if you need help with:
- Installing Git
- Setting up authentication
- Using Netlify
- Using Vercel
- Manual deployment

---

## ✅ Quick Start (Right Now!)

**Fastest deployment (no Git needed):**

```powershell
cd frontend
npm run build
```

Then go to https://app.netlify.com/drop and drag the `dist` folder!

**Your site will be live in 30 seconds!** 🚀
