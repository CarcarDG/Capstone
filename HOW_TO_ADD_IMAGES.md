# How to Add Images to Your Recipe Platform

## 📁 Folder Structure Created

```
frontend/public/
├── avatars/          ← User profile pictures
│   ├── admin.jpg
│   ├── user1.jpg
│   └── user2.jpg
└── images/           ← Recipe, note, and banner images
    ├── recipe1.jpg
    ├── recipe2.jpg
    ├── recipe3.jpg
    ├── recipe4.jpg
    ├── note1.jpg
    ├── note2.jpg
    ├── note3.jpg
    ├── note4.jpg
    ├── banner1.jpg
    ├── banner2.jpg
    └── banner3.jpg
```

## 🖼️ How to Upload Your Images

### Method 1: Drag and Drop (Easiest)
1. Open Windows File Explorer
2. Navigate to: `D:\carso\Documents\Capstone\frontend\public\avatars` (for avatars)
   or `D:\carso\Documents\Capstone\frontend\public\images` (for recipes/notes)
3. Drag your image files into the folder
4. Rename them to match the expected names (e.g., `recipe1.jpg`)

### Method 2: Copy and Paste
1. Find your image file on your computer
2. Right-click → Copy
3. Navigate to the appropriate folder
4. Right-click → Paste
5. Rename if needed

### Method 3: Using Command Line
```powershell
# Copy an image to avatars folder
Copy-Item "C:\path\to\your\image.jpg" "frontend\public\avatars\admin.jpg"

# Copy an image to images folder
Copy-Item "C:\path\to\your\recipe.jpg" "frontend\public\images\recipe1.jpg"
```

## 📐 Image Size Recommendations

| Type | Recommended Size | Aspect Ratio |
|------|-----------------|--------------|
| Avatars | 200x200px | 1:1 (square) |
| Recipe Covers | 800x600px | 4:3 |
| Food Notes | 800x600px | 4:3 |
| Carousel Banners | 1200x400px | 3:1 |

## 🎨 Using Placeholder Images (Temporary)

If you don't have images yet, update the paths to use free placeholder services:

### Option 1: Random Food Photos (Unsplash)
```sql
-- In database/sample_data.sql
'/images/recipe1.jpg' → 'https://source.unsplash.com/800x600/?shrimp,soup'
'/images/recipe2.jpg' → 'https://source.unsplash.com/800x600/?pumpkin,pancake'
```

### Option 2: Lorem Picsum (Random Photos)
```sql
'https://picsum.photos/800/600?random=1'
'https://picsum.photos/800/600?random=2'
```

### Option 3: Placeholder.com (Solid Colors)
```sql
'https://via.placeholder.com/800x600/FF6B6B/FFFFFF?text=Recipe+1'
```

## 🔄 After Adding Images

### If using local images:
1. Place images in `frontend/public/avatars/` or `frontend/public/images/`
2. Keep the paths in SQL as `/avatars/admin.jpg` or `/images/recipe1.jpg`
3. Restart your frontend dev server if it's running

### If using URLs:
1. Update `database/sample_data.sql` with full URLs
2. Update Vue component files with the same URLs
3. Re-run the SQL script to update the database

## 📝 Files to Update

### 1. Database (SQL)
File: `database/sample_data.sql`
- Lines 6, 10-11: User avatars
- Lines 25, 30, 35, 40: Recipe covers
- Lines 48, 51, 54, 57: Note images
- Lines 67-69: Carousel banners

### 2. Frontend (Vue Components)
Files to update if using different paths:
- `frontend/src/stores/auth-mock.js` (avatars)
- `frontend/src/views/user/Recipes.vue` (recipe images)
- `frontend/src/views/user/Notes.vue` (note images)
- `frontend/src/views/user/Collections.vue` (collection images)
- `frontend/src/views/user/MealPlanner.vue` (meal images)

## 🚀 Quick Test

After adding images, test by:
1. Starting the frontend: `cd frontend && npm run dev`
2. Opening browser to `http://localhost:5173`
3. Login and check if images appear

## ❓ Troubleshooting

**Images not showing?**
- Check file names match exactly (case-sensitive on some systems)
- Verify files are in the correct folder
- Clear browser cache (Ctrl + Shift + R)
- Check browser console for 404 errors

**Need different image names?**
- Update paths in both SQL and Vue files
- Keep consistency between database and frontend
