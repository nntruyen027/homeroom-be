DROP FUNCTION IF EXISTS auth.fn_xoa_nguoi_dung;

CREATE OR REPLACE FUNCTION auth.fn_xoa_nguoi_dung(
    p_user_id BIGINT
)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'User ID % không tồn tại', p_user_id;
    END IF;

    DELETE
    FROM auth.users
    WHERE id = p_user_id;

END;
$$;