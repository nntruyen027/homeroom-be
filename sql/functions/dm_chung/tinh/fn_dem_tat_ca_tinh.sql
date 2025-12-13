CREATE OR REPLACE FUNCTION dm_chung.fn_dem_tat_ca_tinh
(
    p_search VARCHAR(500)
)
RETURNS BIGINT
AS $$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT t.id)
    INTO total
    FROM dm_chung.tinh t
    WHERE
        p_search IS NULL OR p_search = ''
        OR unaccent(lower(u.ten)) LIKE '%' || unaccent(lower(p_search)) || '%';
    RETURN total;
END;
$$ LANGUAGE plpgsql;
