DROP FUNCTION IF EXISTS school.fn_lay_giao_vien_theo_id;

CREATE OR REPLACE FUNCTION school.fn_lay_giao_vien_theo_id(
    p_id BIGINT
)
    RETURNS SETOF auth.v_users_giao_vien
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM auth.v_users_giao_vien u
        WHERE u.out_id = p_id
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;