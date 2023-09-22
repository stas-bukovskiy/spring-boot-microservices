CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE "users"
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(50),
    created_at TIMESTAMP
);