DROP FUNCTION IF EXISTS school.fn_dem_tat_ca_truong;

CREATE OR REPLACE FUNCTION school.fn_dem_tat_ca_truong(
    p_search VARCHAR(500)
)
    RETURNS BIGINT
AS
$$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT t.id)
    INTO total
    FROM school.truong t
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(t.ten)) LIKE '%' || unaccent(lower(p_search)) || '%';
    RETURN total;
END;
$$ LANGUAGE plpgsql;
