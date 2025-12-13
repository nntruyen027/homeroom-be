DROP TABLE IF EXISTS auth.permissions;

CREATE TABLE auth.permissions
(
    id   BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL
);