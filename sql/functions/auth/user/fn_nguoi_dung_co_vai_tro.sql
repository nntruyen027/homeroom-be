DROP FUNCTION IF EXISTS auth.fn_nguoi_dung_co_vai_tro;

CREATE OR REPLACE FUNCTION auth.fn_nguoi_dung_co_vai_tro(
    p_user_id BIGINT,
    p_role_code VARCHAR(500)
)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN EXISTS (SELECT 1
                   FROM auth.user_roles ur
                            JOIN auth.roles r ON r.id = ur.role_id
                   WHERE ur.user_id = p_user_id
                     AND r.code = p_role_code);
END;
$$;
