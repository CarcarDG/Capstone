-- Create new tables for Announcements, Notes, Collections, and Meal Plans
-- Run this in Railway MySQL database

-- Announcement Table
CREATE TABLE IF NOT EXISTS announcement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    user_id BIGINT NOT NULL,
    status INT NOT NULL DEFAULT 1 COMMENT '1=active, -1=deleted',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Note Table
CREATE TABLE IF NOT EXISTS note (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    cover_image VARCHAR(255),
    user_id BIGINT NOT NULL,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    status INT NOT NULL DEFAULT 1 COMMENT '1=active, -1=deleted',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Collection Table
CREATE TABLE IF NOT EXISTS collection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    recipe_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (recipe_id) REFERENCES recipe(id),
    UNIQUE KEY unique_user_recipe (user_id, recipe_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Meal Plan Table
CREATE TABLE IF NOT EXISTS meal_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    recipe_id BIGINT NOT NULL,
    plan_date DATE NOT NULL,
    meal_type VARCHAR(20) COMMENT 'breakfast, lunch, dinner, snack',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (recipe_id) REFERENCES recipe(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample announcements
INSERT INTO announcement (title, content, user_id, status) VALUES
('Welcome to Recipe Platform!', 'Thank you for joining our community. Explore delicious recipes and share your culinary creations!', 1, 1),
('New Features Available', 'We have added meal planning and collection features. Check them out!', 1, 1),
('Weekend Cooking Challenge', 'Join our weekend cooking challenge and win prizes!', 1, 1);

-- Insert sample notes
INSERT INTO note (title, content, cover_image, user_id, view_count, like_count, status) VALUES
('My Cooking Journey', 'Started learning to cook this year. Here are my experiences...', 'https://images.unsplash.com/photo-1556910103-1c02745aae4d?w=300&h=200&fit=crop', 2, 45, 12, 1),
('Tips for Perfect Pasta', 'After many attempts, I finally mastered the art of cooking pasta...', 'https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=300&h=200&fit=crop', 2, 78, 23, 1);

SELECT 'Tables created successfully!' AS status;
