DROP TABLE IF EXISTS auth.user_roles;

CREATE TABLE auth.user_roles
(
    user_id BIGINT REFERENCES auth.users (id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES auth.roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

