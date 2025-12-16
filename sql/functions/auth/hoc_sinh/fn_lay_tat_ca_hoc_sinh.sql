DROP FUNCTION IF EXISTS auth.fn_lay_tat_ca_hoc_sinh;

CREATE OR REPLACE FUNCTION auth.fn_lay_tat_ca_hoc_sinh(
    p_lop_id BIGINT DEFAULT NULL,
    p_search VARCHAR(500) DEFAULT NULL,
    p_offset INT DEFAULT 0,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF auth.v_hoc_sinh_full
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT hs.*
        FROM auth.v_hoc_sinh_full hs
        WHERE
          -- lọc theo lớp nếu có truyền
            (p_lop_id IS NULL OR hs.lop_id = p_lop_id)

          -- tìm kiếm theo họ tên
          AND (
            p_search IS NULL
                OR p_search = ''
                OR public.unaccent(lower(hs.ho_ten))
                LIKE '%' || public.unaccent(lower(p_search)) || '%'
            )
        ORDER BY hs.ho_ten
        OFFSET p_offset LIMIT p_limit;
END;
$$;
