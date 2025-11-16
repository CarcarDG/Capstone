# 🚀 Deploy Your Recipe Platform to GitHub Pages

**Your Repository:** https://github.com/CarcarDG/Capstone  
**Live URL (after deployment):** https://carcardg.github.io/Capstone/

---

## ✅ Quick Deploy (3 Steps)

### Step 1: Push Your Code to GitHub

```powershell
git add .
git commit -m "Ready for deployment"
git push origin main
```

### Step 2: Run the Deploy Script

```powershell
cd frontend
.\deploy.bat
```

This will:
- Build your production app
- Create a `gh-pages` branch
- Push to GitHub Pages
- Show you the live URL

### Step 3: Enable GitHub Pages

1. Go to: https://github.com/CarcarDG/Capstone/settings/pages
2. Under "Source", select:
   - **Source:** Deploy from a branch
   - **Branch:** `gh-pages`
   - **Folder:** `/ (root)`
3. Click **Save**

---

## 🎯 Your Live URL

After 2-3 minutes, your site will be live at:

**https://carcardg.github.io/Capstone/**

---

## 🔧 If You Get Authentication Errors

If the deploy script asks for credentials, you have two options:

### Option A: Use GitHub CLI (Recommended)

1. Install GitHub CLI: https://cli.github.com/
2. Login:
   ```powershell
   gh auth login
   ```
3. Run deploy again:
   ```powershell
   .\deploy.bat
   ```

### Option B: Use Personal Access Token

1. Create a token: https://github.com/settings/tokens
   - Click "Generate new token (classic)"
   - Select scope: `repo`
   - Copy the token

2. Update deploy.bat line 28 to:
   ```bat
   git push -f https://YOUR_TOKEN@github.com/CarcarDG/Capstone.git main:gh-pages
   ```

---

## 📱 Test Your Deployment

Once deployed, test these URLs:

- **Home:** https://carcardg.github.io/Capstone/
- **Login:** https://carcardg.github.io/Capstone/login
- **Recipes:** https://carcardg.github.io/Capstone/recipes

**Test Credentials:**
- User: `user@example.com` / `password123`
- Admin: `admin@example.com` / `admin123`

---

## 🔄 Update Your Live Site

Whenever you make changes:

```powershell
git add .
git commit -m "Your update message"
git push origin main
cd frontend
.\deploy.bat
```

Your live site updates in 2-3 minutes!

---

## 🎨 Custom Domain (Optional)

Want to use your own domain like `recipes.yourdomain.com`?

1. Add a `CNAME` file in `frontend/public/`:
   ```
   recipes.yourdomain.com
   ```

2. Update your domain's DNS:
   - Type: `CNAME`
   - Name: `recipes` (or `@` for root)
   - Value: `carcardg.github.io`

3. In GitHub Settings → Pages, add your custom domain

---

## ⚡ Alternative: Deploy to Netlify (Even Easier!)

If you prefer Netlify:

1. Go to: https://app.netlify.com/
2. Click "Add new site" → "Import an existing project"
3. Connect to GitHub: https://github.com/CarcarDG/Capstone
4. Settings:
   - **Base directory:** `frontend`
   - **Build command:** `npm run build`
   - **Publish directory:** `frontend/dist`
5. Click "Deploy"

You'll get a URL like: `https://your-recipe-platform.netlify.app`

---

## 🐛 Troubleshooting

### Issue: 404 on page refresh
**Solution:** Already fixed! Your vite.config.js has the correct base path.

### Issue: Images not loading
**Solution:** Use relative paths in your code:
```javascript
// Good
<img src="/images/recipe.jpg" />

// Bad
<img src="images/recipe.jpg" />
```

### Issue: API calls failing
**Solution:** This is expected! You're using mock data. To connect to a real backend:
1. Deploy your backend to a service (Heroku, Railway, etc.)
2. Update API URLs in your frontend
3. Enable CORS on your backend

---

## 📊 Deployment Checklist

- [ ] Code pushed to GitHub
- [ ] Deploy script executed successfully
- [ ] GitHub Pages enabled in settings
- [ ] Waited 2-3 minutes for deployment
- [ ] Tested live URL
- [ ] Login works
- [ ] All pages load correctly
- [ ] Responsive design works on mobile

---

## 🎉 Ready to Deploy?

Run these commands now:

```powershell
# Make sure you're in the project root
git add .
git commit -m "Ready for GitHub Pages deployment"
git push origin main

# Deploy to GitHub Pages
cd frontend
.\deploy.bat
```

Then visit: **https://carcardg.github.io/Capstone/**

---

**Need help?** Check the deployment logs or ask for assistance!
