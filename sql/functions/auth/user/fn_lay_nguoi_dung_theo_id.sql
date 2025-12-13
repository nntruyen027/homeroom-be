DROP FUNCTION IF EXISTS auth.fn_lay_nguoi_dung_theo_id;

CREATE OR REPLACE FUNCTION auth.fn_lay_nguoi_dung_theo_id(
    p_id BIGINT
)
    RETURNS SETOF auth.v_users_admin
AS
$$
BEGIN
    IF NOT EXISTS(SELECT 1 FROM users WHERE id = p_id) THEN
        RAISE EXCEPTION 'Người dùng với id % không tồn tại', p_id;
    end if;

    RETURN QUERY SELECT *
                 FROM auth.v_users_admin
                 WHERE out_id = p_id
                 LIMIT 1;
end;

$$ LANGUAGE plpgsql;