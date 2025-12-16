DROP FUNCTION IF EXISTS auth.fn_lay_quan_tri_theo_username;

CREATE OR REPLACE FUNCTION auth.fn_lay_quan_tri_theo_username(
    p_username VARCHAR
)
    RETURNS SETOF auth.v_users_full
AS
$$
BEGIN
    IF NOT EXISTS (select 1 from auth.users u where u.username = p_username) THEN
        RAISE EXCEPTION 'Quản trị viên với tên đăng nhập: % không tồn tại.', p_username;
    END IF;

    RETURN QUERY
        SELECT *
        FROM auth.v_users_full u
        WHERE u.username = p_username
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
