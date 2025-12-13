-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_lay_permission_theo_user;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_lay_permission_theo_user(
    p_user_id BIGINT
)
    RETURNS TABLE
            (
                permission_code VARCHAR(100)
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT DISTINCT p.code
        FROM auth.user_roles ur
                 JOIN auth.role_permissions rp ON ur.role_id = rp.role_id
                 JOIN auth.permissions p ON rp.permission_id = p.id
        WHERE ur.user_id = p_user_id;
END;
$$;
