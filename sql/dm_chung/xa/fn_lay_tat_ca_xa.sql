DROP FUNCTION IF EXISTS dm_chung.fn_lay_tat_ca_xa;

CREATE OR REPLACE FUNCTION dm_chung.fn_lay_tat_ca_xa
(
    p_search   VARCHAR(500),
    p_tinh_id  BIGINT,
    p_offset   INT DEFAULT 1,
    p_limit    INT DEFAULT 10
)
RETURNS TABLE
(
    out_id     BIGINT,
    ten        VARCHAR(120),
    ghi_chu    TEXT,
    tinh_id    BIGINT,
    ten_tinh   VARCHAR(120)
)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        x.id,
        x.ten,
        x.ghi_chu,
        x.tinh_id,
        t.ten AS ten_tinh
    FROM dm_chung.xa x
    LEFT JOIN dm_chung.tinh t ON t.id = x.tinh_id
    WHERE
        (p_search IS NULL OR p_search = ''
         OR unaccent(lower(x.ten)) LIKE '%' || unaccent(lower(p_search)) || '%')
        AND
        (p_tinh_id IS NULL OR x.tinh_id = p_tinh_id)
    ORDER BY t.ten, x.ten
    OFFSET p_offset * p_limit
    LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
