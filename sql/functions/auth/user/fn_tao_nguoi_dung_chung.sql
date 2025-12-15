DROP FUNCTION IF EXISTS auth.fn_tao_nguoi_dung;
CREATE FUNCTION auth.fn_tao_nguoi_dung(
    p_username TEXT,
    p_password TEXT,
    p_ho_ten TEXT,
    p_avatar TEXT DEFAULT NULL
)
    RETURNS BIGINT
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_user_id BIGINT;
BEGIN
    IF EXISTS (SELECT 1 FROM auth.users WHERE username = p_username) THEN
        RAISE EXCEPTION 'Username % đã tồn tại', p_username;
    END IF;

    INSERT INTO auth.users(username, password, ho_ten, avatar)
    VALUES (p_username, p_password, p_ho_ten, p_avatar)
    RETURNING id INTO v_user_id;

    RETURN v_user_id;
END;
$$;
