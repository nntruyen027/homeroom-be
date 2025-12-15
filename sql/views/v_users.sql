DROP VIEW IF EXISTS auth.v_users_full CASCADE;
CREATE VIEW auth.v_users_full AS
SELECT u.id,
       u.username,
       u.ho_ten,
       u.avatar,
       u.is_active,
       u.created_at
FROM auth.users u;
