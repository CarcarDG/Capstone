-- Quick Database Check Script
-- Run this in TablePlus or MySQL Workbench to verify data

-- Check if tables exist
SHOW TABLES;

-- Check category count
SELECT 'Categories:' as Info, COUNT(*) as Count FROM recipe_category;

-- Check recipe count
SELECT 'Recipes:' as Info, COUNT(*) as Count FROM recipe;

-- Check user count
SELECT 'Users:' as Info, COUNT(*) as Count FROM user;

-- View all categories
SELECT id, name, description, sort_order FROM recipe_category ORDER BY sort_order;

-- View all recipes (basic info)
SELECT 
    id, 
    title, 
    difficulty, 
    cooking_time, 
    servings,
    status,
    view_count,
    like_count
FROM recipe 
WHERE status = 1
ORDER BY created_at DESC;

-- Check if admin user exists
SELECT id, username, role FROM user WHERE role = 'ADMIN';

-- Summary
SELECT 
    (SELECT COUNT(*) FROM recipe_category) as categories,
    (SELECT COUNT(*) FROM recipe WHERE status = 1) as published_recipes,
    (SELECT COUNT(*) FROM user) as users,
    (SELECT COUNT(*) FROM food_note) as notes;
