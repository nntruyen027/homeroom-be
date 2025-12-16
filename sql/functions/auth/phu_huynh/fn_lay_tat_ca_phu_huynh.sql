DROP FUNCTION IF EXISTS auth.fn_lay_tat_ca_phu_huynh;

CREATE OR REPLACE FUNCTION auth.fn_lay_tat_ca_phu_huynh(
    p_search VARCHAR(500),
    p_offset INT DEFAULT 0,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF auth.v_phu_huynh_full
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT hs.*
        FROM auth.v_phu_huynh_full hs
        WHERE p_search IS NULL
           OR p_search = ''
           OR (
            public.unaccent(lower(hs.ho_ten)) LIKE '%' || public.unaccent(lower(p_search)) || '%'
            )
        ORDER BY hs.ho_ten
        OFFSET p_offset LIMIT p_limit;
END;
$$;
