DROP FUNCTION IF EXISTS auth.fn_doi_mat_khau;

CREATE OR REPLACE FUNCTION auth.fn_doi_mat_khau(
    p_user_id BIGINT,
    p_mat_khau_moi TEXT
)
    RETURNS BOOLEAN
AS
$$
BEGIN
    IF EXISTS(SELECT 1 FROM auth.users where id = p_user_id) THEN
        RAISE EXCEPTION 'Người dùng với id % không tồn tại', p_user_id;
    end if;

    UPDATE auth.users
    SET password = p_mat_khau_moi
    WHERE id = p_user_id;


    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;