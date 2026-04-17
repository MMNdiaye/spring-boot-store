CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE addresses(
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE categories(
    id SMALLSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE products(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    category_id SMALLINT NOT NULL REFERENCES categories(id)
);

CREATE TABLE profiles (
    id BIGSERIAL PRIMARY KEY REFERENCES profiles(id),
    bio TEXT,
    phone_number VARCHAR(15),
    date_of_birth DATE,
    loyalty_points INT CHECK(loyalty_points > 0) DEFAULT 0
);


CREATE TABLE wishlist(
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    user_id    BIGINT NOT NULL REFERENCES users(id),
    PRIMARY KEY(product_id, user_id)
);

CREATE INDEX addresses_users_id_fk ON addresses (user_id);

CREATE INDEX fk_category ON products (category_id);

CREATE INDEX fk_wishlist_on_user ON wishlist (user_id);