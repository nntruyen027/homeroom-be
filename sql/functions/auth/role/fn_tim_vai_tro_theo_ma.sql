-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_lay_vai_tro_theo_ma;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_lay_vai_tro_theo_ma(
    p_code VARCHAR(50)
)
    RETURNS SETOF auth.v_role
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM auth.v_role r
        WHERE r.code = p_code;
END;
$$;
