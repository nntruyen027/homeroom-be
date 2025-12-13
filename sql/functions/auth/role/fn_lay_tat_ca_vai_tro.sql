DROP FUNCTION IF EXISTS auth.fn_lay_tat_ca_vai_tro;

CREATE OR REPLACE FUNCTION auth.fn_lay_tat_ca_vai_tro(
    p_search VARCHAR(100),
    p_offset INT DEFAULT 0,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF auth.v_role
AS
$$
BEGIN
    SELECT *
    FROM auth.v_role r
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(r.name)) LIKE '%' || unaccent(lower(p_search)) || '%'
       OR unaccent(lower(r.code)) LIKE '%' || unaccent(lower(p_search)) || '%'
    ORDER BY r.name
    OFFSET p_offset LIMIT p_limit;
end;

$$ LANGUAGE plpgsql;