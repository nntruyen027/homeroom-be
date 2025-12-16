DROP FUNCTION IF EXISTS fn_lay_tat_ca_file;

CREATE OR REPLACE FUNCTION fn_lay_tat_ca_file(
    p_search VARCHAR(500),
    p_offset INT DEFAULT 0,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF v_file
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT f.*
        FROM v_file f
        WHERE p_search IS NULL
           OR p_search = ''
           OR (
            public.unaccent(lower(f.file_name)) LIKE '%' || public.unaccent(lower(p_search)) || '%'
            )
        ORDER BY f.file_name
        OFFSET p_offset LIMIT p_limit;
END;
$$;
