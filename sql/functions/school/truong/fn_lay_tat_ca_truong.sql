DROP FUNCTION IF EXISTS school.fn_lay_tat_ca_truong;

CREATE OR REPLACE FUNCTION school.fn_lay_tat_ca_truong(
    p_search VARCHAR(500),
    p_offset INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF school.v_truong
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM school.v_truong t
        WHERE p_search IS NULL
           OR p_search = ''
           OR unaccent(lower(t.ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
        ORDER BY t.ten
        OFFSET p_offset LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
