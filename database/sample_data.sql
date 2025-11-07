-- Sample Data for Recipe Platform
USE recipe_platform;

-- Insert Admin User (password: admin123)
INSERT INTO user (username, password, nickname, email, phone, avatar, role) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'Administrator', 'admin@example.com', '1234567890', 'https://i.pravatar.cc/200?img=12', 'ADMIN');

-- Insert Regular Users (password: user123)
INSERT INTO user (username, password, nickname, email, phone, avatar, role) VALUES
('john', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'John Doe', 'john@example.com', '1234567891', 'https://i.pravatar.cc/200?img=33', 'USER'),
('jane', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'Jane Smith', 'jane@example.com', '1234567892', 'https://i.pravatar.cc/200?img=47', 'USER');

-- Insert Recipe Categories
INSERT INTO recipe_category (name, description, icon, sort_order) VALUES
('Main Dishes', 'Daily home cooking dishes', '🍲', 1),
('Quick Meals', 'Simple and fast dishes', '⚡', 2),
('Soups', 'Various soups and stews', '🥣', 3),
('Baking', 'Bread, cakes and baked goods', '🍰', 4),
('Desserts', 'Various desserts', '🍮', 5),
('Vegetarian', 'Vegetarian dishes', '🥗', 6),
('Meat Dishes', 'Meat-based dishes', '🍖', 7);

-- Insert Sample Recipes
INSERT INTO recipe (title, description, cover_image, category_id, user_id, ingredients, steps, cooking_time, difficulty, servings, tips, view_count, like_count, collect_count, comment_count, is_featured) VALUES
('Healthy Shrimp Mushroom Tofu Soup', 'Low-calorie, high-protein nutritious healthy soup', 'https://images.unsplash.com/photo-1547592166-23ac45744acd?w=800&h=600&fit=crop', 3, 2, 
'[{"name":"Shrimp","amount":"200g"},{"name":"Mushrooms","amount":"150g"},{"name":"Tofu","amount":"1 block"},{"name":"Ginger and scallions","amount":"to taste"}]',
'[{"step":1,"description":"Clean shrimp and devein, slice mushrooms, cube tofu"},{"step":2,"description":"Boil water in pot, add ginger slices"},{"step":3,"description":"After water boils, add tofu and mushrooms, cook for 5 minutes"},{"step":4,"description":"Add shrimp and cook until they change color"},{"step":5,"description":"Season with salt and sprinkle scallions"}]',
30, 'EASY', 2, 'Do not overcook shrimp, just until color changes to keep tender texture', 1234, 89, 56, 23, 1),

('Low-Calorie Sweet Pumpkin Pancakes', 'Healthy and delicious pumpkin treats', 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=800&h=600&fit=crop', 5, 2,
'[{"name":"Pumpkin","amount":"300g"},{"name":"Glutinous rice flour","amount":"200g"},{"name":"Sugar","amount":"30g"}]',
'[{"step":1,"description":"Peel pumpkin, cut into pieces and steam until soft"},{"step":2,"description":"While hot, add sugar and mash into puree"},{"step":3,"description":"Gradually add glutinous rice flour and knead into dough"},{"step":4,"description":"Divide into small portions and flatten"},{"step":5,"description":"Pan fry on low heat until golden on both sides"}]',
45, 'EASY', 4, 'Dough should not be too soft, or it will be hard to shape', 2341, 156, 89, 45, 1),

('Potato Shred Egg Pancake', 'Simple and quick breakfast option', 'https://images.unsplash.com/photo-1525351484163-7529414344d8?w=800&h=600&fit=crop', 2, 2,
'[{"name":"Potatoes","amount":"2"},{"name":"Eggs","amount":"2"},{"name":"Flour","amount":"50g"},{"name":"Chopped scallions","amount":"to taste"}]',
'[{"step":1,"description":"Shred potatoes and rinse with water to remove starch"},{"step":2,"description":"Drain water, add eggs, flour, scallions, and salt"},{"step":3,"description":"Mix evenly into batter"},{"step":4,"description":"Brush oil in pan, pour batter and spread evenly"},{"step":5,"description":"Cook on low heat until golden on both sides"}]',
20, 'EASY', 2, 'Shred potatoes as fine as possible for better texture', 3456, 234, 123, 67, 1),

('Comfort Food That Heals the Soul', 'Exquisite and delicious creative cuisine', 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=800&h=600&fit=crop', 1, 2,
'[{"name":"Main ingredients","amount":"according to personal preference"},{"name":"Seasonings","amount":"to taste"}]',
'[{"step":1,"description":"Prepare required ingredients"},{"step":2,"description":"Cook according to steps"},{"step":3,"description":"Plate and garnish"}]',
60, 'MEDIUM', 3, 'Pay attention to heat control', 4567, 345, 234, 89, 1);

-- Insert Sample Food Notes
INSERT INTO food_note (title, content, images, user_id, recipe_id, tags, view_count, like_count, collect_count, comment_count) VALUES
('Braised Pork Belly (Super Detailed)', 'Made super delicious braised pork today, fatty but not greasy, melts in your mouth! Sharing the detailed recipe with everyone. First select pork belly, cut into 2cm square pieces...', 
'["https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?w=800&h=600&fit=crop"]', 2, NULL, 'braised pork,home cooking', 5678, 456, 234, 78),

('Peanut Red Bean Milk Smoothie', 'Nutritious breakfast drink, rich and delicious', 
'["https://images.unsplash.com/photo-1553530666-ba11a7da3888?w=800&h=600&fit=crop"]', 2, NULL, 'breakfast,drinks', 3456, 234, 123, 45),

('Juicy Dumplings - Irresistibly Good!', 'Super delicious snack recipe', 
'["https://images.unsplash.com/photo-1496116218417-1a781b1c416c?w=800&h=600&fit=crop"]', 2, NULL, 'snacks,desserts', 6789, 567, 345, 123),

('Braised Spare Ribs', 'Classic home-style dish, bright red color, delicious taste', 
'["https://images.unsplash.com/photo-1544025162-d76694265947?w=800&h=600&fit=crop"]', 2, NULL, 'spare ribs,home cooking', 4567, 345, 234, 67);

-- Insert Announcements
INSERT INTO announcement (title, content, type, priority, created_by) VALUES
('Beautiful Weather Today, Perfect for a Walk!', 'Spring is here with blooming flowers, perfect for outdoor activities. Starting work mode!', 'SYSTEM', 1, 1),
('All Features Complete and Ready to Use!', 'All platform features have been developed and completed. Welcome everyone to use them!', 'SYSTEM', 2, 1),
('Another Energetic Day, Let''s Go Have Fun!', 'Happy weekend! Wishing everyone a great time!', 'EVENT', 1, 1);

-- Insert Carousel Items
INSERT INTO carousel (title, image_url, link_url, sort_order) VALUES
('Featured Food Recommendations', 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=1200&h=400&fit=crop', '/recipes/featured', 1),
('Beginner Tutorial Guide', 'https://images.unsplash.com/photo-1556910103-1c02745aae4d?w=1200&h=400&fit=crop', '/tutorials', 2),
('This Week''s Popular Recipes', 'https://images.unsplash.com/photo-1547592180-85f173990554?w=1200&h=400&fit=crop', '/recipes/hot', 3);

-- Insert Sample Comments
INSERT INTO comment (content, user_id, target_type, target_id) VALUES
('Looks very delicious!', 3, 'RECIPE', 1),
('Saved to favorites, will try it', 3, 'RECIPE', 2),
('Very detailed instructions, thanks for sharing', 2, 'NOTE', 1);

-- Insert Sample Collections
INSERT INTO collection (user_id, target_type, target_id) VALUES
(3, 'RECIPE', 1),
(3, 'RECIPE', 2),
(2, 'NOTE', 1);

-- Insert Sample Likes
INSERT INTO user_like (user_id, target_type, target_id) VALUES
(3, 'RECIPE', 1),
(3, 'RECIPE', 2),
(2, 'NOTE', 1),
(3, 'NOTE', 2);

-- Insert Sample Meal Plans
INSERT INTO meal_plan (user_id, recipe_id, plan_date, meal_type, notes) VALUES
(2, 1, CURDATE(), 'LUNCH', 'Having this for lunch today'),
(2, 2, CURDATE(), 'DINNER', 'Dinner preparation'),
(2, 3, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'BREAKFAST', 'Tomorrow''s breakfast');
