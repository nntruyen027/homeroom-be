DROP TABLE IF EXISTS auth.users;

CREATE TABLE auth.users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(120) UNIQUE NOT NULL,
    password   VARCHAR(500)        NOT NULL,

    ho_ten     VARCHAR(500)        NOT NULL,
    avatar     VARCHAR(500),

    is_active  BOOLEAN   DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT now()
);
