DROP FUNCTION IF EXISTS auth.fn_lay_giao_vien_theo_username;

CREATE OR REPLACE FUNCTION auth.fn_lay_giao_vien_theo_username(
    p_username VARCHAR
)
    RETURNS SETOF auth.v_users_giao_vien
AS
$$
BEGIN
    -- Kiểm tra giáo viên tồn tại (qua VIEW)
    IF NOT EXISTS (SELECT 1
                   FROM auth.v_users_giao_vien gv
                   WHERE gv.username = p_username) THEN
        RAISE EXCEPTION 'Giáo viên với tên đăng nhập % không tồn tại.', p_username;
    END IF;

    RETURN QUERY
        SELECT *
        FROM auth.v_users_giao_vien
        WHERE username = p_username
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
