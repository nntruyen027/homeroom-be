DROP FUNCTION IF EXISTS dm_chung.fn_lay_tat_ca_xa;

CREATE OR REPLACE FUNCTION dm_chung.fn_lay_tat_ca_xa(
    p_search VARCHAR(500),
    p_tinh_id BIGINT,
    p_offset INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF dm_chung.v_xa
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM dm_chung.v_xa x
        WHERE (p_search IS NULL OR p_search = ''
            OR unaccent(lower(x.ten)) LIKE '%' || unaccent(lower(p_search)) || '%')
          AND (p_tinh_id IS NULL OR x.tinh_id = p_tinh_id)
        ORDER BY x.ten_tinh, x.ten
        OFFSET p_offset LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
