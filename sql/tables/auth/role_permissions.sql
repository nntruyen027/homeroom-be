DROP TABLE IF EXISTS auth.role_permissions;

CREATE TABLE auth.role_permissions
(
    role_id       BIGINT REFERENCES auth.roles (id),
    permission_id BIGINT REFERENCES auth.permissions (id),
    PRIMARY KEY (role_id, permission_id)
);