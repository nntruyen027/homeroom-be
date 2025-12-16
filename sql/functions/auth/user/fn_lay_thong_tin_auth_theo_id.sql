DROP FUNCTION IF EXISTS auth.fn_lay_thong_tin_auth_theo_id;

CREATE OR REPLACE FUNCTION auth.fn_lay_thong_tin_auth_theo_id(
    p_id BIGINT
)
    RETURNS SETOF auth.v_thong_tin_auth
AS
$$
BEGIN
    SELECT * FROM auth.v_thong_tin_auth u WHERE u.id = p_id LIMIT 1;
end;

$$ LANGUAGE plpgsql;