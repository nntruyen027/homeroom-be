DROP FUNCTION IF EXISTS auth.fn_tao_permission;

CREATE OR REPLACE FUNCTION auth.fn_tao_permission(
    p_code VARCHAR(100)
)
    RETURNS SETOF auth.v_permission
AS
$$
BEGIN
    IF EXISTS(SELECT 1 FROM auth.permissions WHERE permissions.code = p_code) THEN
        RAISE EXCEPTION 'Permission với mã % đã tồn tại', p_code;
    end if;

    INSERT INTO auth.permissions(code) VALUES (p_code);


    RETURN QUERY SELECT * from auth.v_permission where code = p_code;
end;
$$ LANGUAGE plpgsql;