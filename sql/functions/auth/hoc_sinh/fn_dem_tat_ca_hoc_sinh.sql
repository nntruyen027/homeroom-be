DROP FUNCTION IF EXISTS auth.fn_dem_tat_ca_hoc_sinh;

CREATE OR REPLACE FUNCTION auth.fn_dem_tat_ca_hoc_sinh(
    p_lop_id BIGINT DEFAULT NULL,
    p_search VARCHAR(500) DEFAULT NULL
)
    RETURNS BIGINT
    LANGUAGE plpgsql
AS
$$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT hs.user_id)
    INTO total
    FROM auth.hoc_sinh hs
             JOIN auth.users u ON u.id = hs.user_id
    WHERE
      -- lọc theo lớp nếu có truyền
        (p_lop_id IS NULL OR hs.lop_id = p_lop_id)

      -- tìm kiếm theo họ tên
      AND (
        p_search IS NULL
            OR p_search = ''
            OR public.unaccent(lower(u.ho_ten))
            LIKE '%' || public.unaccent(lower(p_search)) || '%'
        );

    RETURN total;
END;
$$;
