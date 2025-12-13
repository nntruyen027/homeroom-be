DROP FUNCTION IF EXISTS auth.fn_lay_nguoi_dung_theo_username(VARCHAR);

CREATE OR REPLACE FUNCTION auth.fn_lay_nguoi_dung_theo_username(
    p_username VARCHAR(120)
)
    RETURNS SETOF auth.v_users_full
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM auth.v_users_full
        WHERE username = p_username
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
