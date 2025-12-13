DROP FUNCTION IF EXISTS auth.fn_lay_permission_theo_code;

CREATE OR REPLACE FUNCTION auth.fn_lay_permission_theo_code(
    p_code VARCHAR(100)
)
    RETURNS SETOF auth.v_permission
AS
$$
BEGIN
    IF NOT EXISTS(SELECT 1 FROM auth.permissions WHERE permissions.code = p_code) THEN
        RAISE EXCEPTION 'Permisson với mã % không tồn tại.', p_code;
    end if;

    RETURN QUERY SELECT * FROM auth.v_permission WHERE code = p_code;
end;

$$ LANGUAGE plpgsql;
