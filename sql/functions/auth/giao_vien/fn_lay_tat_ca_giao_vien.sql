DROP FUNCTION IF EXISTS dm_chung.fn_lay_tat_ca_giao_vien;

CREATE OR REPLACE FUNCTION dm_chung.fn_lay_tat_ca_giao_vien(
    p_search VARCHAR(500),
    p_offset INT DEFAULT 0,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF auth.v_giao_vien_full
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT gv.*
        FROM auth.v_giao_vien_full gv
        WHERE p_search IS NULL
           OR p_search = ''
           OR (
            public.unaccent(lower(gv.ho_ten)) LIKE '%' || public.unaccent(lower(p_search)) || '%'
            )
        ORDER BY gv.ho_ten
        OFFSET p_offset LIMIT p_limit;
END;
$$;
