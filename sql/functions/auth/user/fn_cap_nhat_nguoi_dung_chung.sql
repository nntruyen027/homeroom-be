DROP FUNCTION IF EXISTS auth.fn_cap_nhat_nguoi_dung;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_nguoi_dung(
    p_user_id BIGINT,
    p_ho_ten VARCHAR(500),
    p_avatar TEXT,
    p_is_active BOOLEAN
)
    RETURNS SETOF auth.v_users_full
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'User ID % không tồn tại', p_user_id;
    END IF;

    UPDATE auth.users
    SET ho_ten    = p_ho_ten,
        avatar    = p_avatar,
        is_active = p_is_active
    WHERE id = p_user_id;

    RETURN QUERY
        SELECT *
        FROM auth.v_users_full
        WHERE id = p_user_id;
END;
$$;
