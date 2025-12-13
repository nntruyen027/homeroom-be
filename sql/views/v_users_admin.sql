DROP VIEW IF EXISTS auth.v_users_admin;

CREATE OR REPLACE VIEW auth.v_users_admin AS
SELECT u.id                    AS out_id,
       u.username,
       u.ho_ten,
       u.avatar,
       string_agg(r.code, ',') AS roles
FROM auth.users u
         JOIN auth.user_roles ur ON ur.user_id = u.id
         JOIN auth.roles r ON r.id = ur.role_id
WHERE u.id IN (SELECT user_id
               FROM auth.user_roles ur2
                        JOIN auth.roles r2 ON r2.id = ur2.role_id
               WHERE r2.code = 'ADMIN')
GROUP BY u.id;
