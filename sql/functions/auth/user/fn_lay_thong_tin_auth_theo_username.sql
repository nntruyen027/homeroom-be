DROP FUNCTION IF EXISTS auth.fn_lay_thong_tin_auth_theo_username;

CREATE OR REPLACE FUNCTION auth.fn_lay_thong_tin_auth_theo_username(
    p_username VARCHAR(120)
)
    RETURNS SETOF auth.v_thong_tin_auth
AS
$$
BEGIN
    RETURN QUERY SELECT * FROM auth.v_thong_tin_auth u WHERE u.username = p_username LIMIT 1;
end;

$$ LANGUAGE plpgsql;