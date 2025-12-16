DROP FUNCTION IF EXISTS fn_dem_tat_ca_file;

CREATE OR REPLACE FUNCTION fn_dem_tat_ca_file(
    p_search VARCHAR(500)
)
    RETURNS BIGINT
    LANGUAGE plpgsql
AS
$$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT f.id)
    INTO total
    FROM files f
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(f.file_name)) LIKE '%' || unaccent(lower(p_search)) || '%';

    RETURN total;
END;
$$;



