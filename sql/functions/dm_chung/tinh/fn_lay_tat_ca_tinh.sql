DROP FUNCTION IF EXISTS dm_chung.fn_lay_tat_ca_tinh;

CREATE OR REPLACE FUNCTION dm_chung.fn_lay_tat_ca_tinh(
    p_search VARCHAR(500),
    p_offset INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF dm_chung.tinh
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM dm_chung.v_tinh t
        WHERE p_search IS NULL
           OR p_search = ''
           OR unaccent(lower(t.ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
        ORDER BY t.ten
        OFFSET p_offset LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
