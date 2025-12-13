DROP FUNCTION IF EXISTS school.fn_lay_hoc_sinh_theo_lop;

CREATE OR REPLACE FUNCTION school.fn_lay_hoc_sinh_theo_lop(
    p_lop_id BIGINT,
    p_search VARCHAR(500),
    p_offset INT DEFAULT 0,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF auth.v_users_hoc_sinh
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM auth.v_users_hoc_sinh hs
        WHERE hs.lop_id = p_lop_id
          AND (
            p_search IS NULL OR p_search = ''
                OR unaccent(lower(hs.ho_ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
                OR unaccent(lower(hs.username)) LIKE '%' || unaccent(lower(p_search)) || '%'
            )
        ORDER BY hs.ho_ten
        OFFSET p_offset LIMIT p_limit;
END;
$$;
