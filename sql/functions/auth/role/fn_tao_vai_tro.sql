DROP FUNCTION IF EXISTS auth.fn_tao_vai_tro;

CREATE OR REPLACE FUNCTION auth.fn_tao_vai_tro(
    p_name VARCHAR(500),
    p_code VARCHAR(50)
)
    RETURNS SETOF auth.v_role
AS
$$
BEGIN
    IF EXISTS(SELECT 1 FROM auth.v_role WHERE code = p_code) THEN
        RAISE EXCEPTION 'Vai trò % với mã % đã tồn tại', p_name, p_code;
    END IF;

    INSERT INTO auth.roles(code, name)
    VALUES (p_code, p_name);

    RETURN QUERY
        SELECT *
        FROM auth.v_role
        WHERE code = p_code
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
