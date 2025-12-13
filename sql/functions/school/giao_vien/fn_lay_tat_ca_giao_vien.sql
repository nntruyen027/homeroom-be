DROP FUNCTION IF EXISTS school.fn_lay_tat_ca_giao_vien;

CREATE OR REPLACE FUNCTION school.fn_lay_tat_ca_giao_vien(
    p_search VARCHAR(500),
    p_offset INT DEFAULT 0,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF auth.v_users_giao_vien
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM auth.v_users_giao_vien u
        WHERE (
                  p_search IS NULL OR p_search = ''
                      OR unaccent(lower(u.ho_ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
                      OR unaccent(lower(u.username)) LIKE '%' || unaccent(lower(p_search)) || '%'
                  )
        ORDER BY u.ho_ten
        OFFSET p_offset LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
