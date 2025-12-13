-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_dem_tat_ca_vai_tro;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_dem_tat_ca_vai_tro(
    p_search VARCHAR(100)
)
    RETURNS BIGINT
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count BIGINT;
BEGIN
    SELECT COUNT(*)
    INTO v_count
    FROM auth.v_role r
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(r.name)) LIKE '%' || unaccent(lower(p_search)) || '%'
       OR unaccent(lower(r.code)) LIKE '%' || unaccent(lower(p_search)) || '%';

    RETURN v_count;
END;
$$;
