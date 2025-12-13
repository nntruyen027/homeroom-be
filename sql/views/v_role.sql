DROP VIEW IF EXISTS auth.v_role;

CREATE OR REPLACE VIEW auth.v_role AS
SELECT r.id                                                                              AS out_id,
       r.name,
       r.code,
       COALESCE(array_agg(p.code ORDER BY p.code) FILTER (WHERE p.id IS NOT NULL), '{}') AS permissions
FROM auth.roles r
         LEFT JOIN auth.role_permissions rp ON r.id = rp.role_id
         LEFT JOIN auth.permissions p ON rp.permission_id = p.id
GROUP BY r.id, r.name, r.code;
