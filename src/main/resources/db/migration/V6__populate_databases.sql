INSERT INTO categories VALUES
(1 ,'Fruits'),
(2, 'Vegetables'),
(3, 'Dairy'),
(4, 'Beverages'),
(5, 'Meat'),
(6, 'Bakery'),
(7, 'Snacks'),
(8, 'Frozen Foods');

INSERT INTO products (name, price, description, category_id) VALUES
-- Fruits
('Bananas (1kg)', 1.50, 'Fresh ripe bananas, rich in potassium', 1),
-- Vegetables
('Carrots (1kg)', 1.20, 'Organic carrots, great for cooking and salads', 2),
-- Dairy
('Whole Milk (1L)', 1.00, 'Fresh whole milk, pasteurized', 3),
-- Beverages
('Coca-Cola (1.5L)', 1.75, 'Carbonated soft drink', 4),
-- Meat
('Chicken Breast (500g)', 3.50, 'Boneless skinless chicken breast', 5),
-- Bakery
('White Bread Loaf', 1.30, 'Soft sliced white bread', 6),
-- Snacks
('Lay''s Classic Chips (200g)', 2.00, 'Crispy salted potato chips', 7),
-- Frozen Foods
('Frozen French Fries (1kg)', 2.80, 'Pre-cut frozen fries', 8),
-- Additional variety
('Apples (1kg)', 2.20, 'Fresh red apples', 1),
('Cheddar Cheese (200g)', 2.90, 'Aged cheddar cheese block', 3);