DROP VIEW IF EXISTS auth.v_users_full;

CREATE VIEW auth.v_users_full AS
SELECT u.id,
       u.username,
       u.ho_ten,
       u.avatar,
       u.is_active,
       u.created_at,

    /* ===== DANH SÁCH ROLE CODE ===== */
       COALESCE(
                       array_agg(DISTINCT r.code) FILTER (WHERE r.code IS NOT NULL),
                       ARRAY []::VARCHAR[]
       ) AS roles,

    /* ===== DANH SÁCH PERMISSION CODE ===== */
       COALESCE(
                       array_agg(DISTINCT p.code) FILTER (WHERE p.code IS NOT NULL),
                       ARRAY []::VARCHAR[]
       ) AS permissions

FROM auth.users u
         LEFT JOIN auth.user_roles ur ON ur.user_id = u.id
         LEFT JOIN auth.roles r ON r.id = ur.role_id
         LEFT JOIN auth.role_permissions rp ON rp.role_id = r.id
         LEFT JOIN auth.permissions p ON p.id = rp.permission_id
GROUP BY u.id;
