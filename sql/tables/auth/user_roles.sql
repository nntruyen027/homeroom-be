DROP TABLE IF EXISTS auth.user_roles;

CREATE TABLE auth.user_roles
(
    user_id BIGINT REFERENCES auth.users (id),
    role_id BIGINT REFERENCES auth.roles (id),
    PRIMARY KEY (user_id, role_id)
);