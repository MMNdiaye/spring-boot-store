CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE TABLE carts(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE
);



CREATE TABLE cart_items(
    id SERIAL PRIMARY KEY,
    cart_id UUID NOT NULL REFERENCES carts(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    quantity INT NOT NULL CHECK (quantity > 0),
    UNIQUE(cart_id, product_id)
);