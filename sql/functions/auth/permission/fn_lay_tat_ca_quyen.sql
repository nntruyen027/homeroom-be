DROP FUNCTION IF EXISTS auth.fn_lay_tat_ca_quyen;

CREATE OR REPLACE FUNCTION auth.fn_lay_tat_ca_quyen(
    p_search VARCHAR(100),
    p_offset INT DEFAULT 0,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF auth.v_permission
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM auth.v_permission p
        WHERE p_search IS NULL
           OR p_search = ''
           OR unaccent(lower(p.code)) LIKE '%' || unaccent(lower(p_search)) || '%'
        ORDER BY p.code
        OFFSET p_offset LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
