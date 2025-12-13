CREATE OR REPLACE FUNCTION dm_chung.fn_dem_tat_ca_xa
(
    p_search VARCHAR(500),
    p_tinh_id   BIGINT
)
RETURNS BIGINT
AS $$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT x.id)
    INTO total
    FROM dm_chung.xa x
    WHERE
        (
            p_search IS NULL OR p_search = ''
            OR unaccent(lower(x.ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
        )
        AND (
            p_tinh_id IS NULL
            OR x.tinh_id = p_tinh_id
        );

    RETURN total;
END;
$$ LANGUAGE plpgsql;
