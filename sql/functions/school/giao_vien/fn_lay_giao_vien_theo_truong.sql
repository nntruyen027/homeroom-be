DROP FUNCTION IF EXISTS school.fn_lay_giao_vien_theo_truong;

CREATE OR REPLACE FUNCTION school.fn_lay_giao_vien_theo_truong(
    p_truong_id BIGINT,
    p_search VARCHAR(500),
    p_offset INT DEFAULT 0,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF auth.v_users_giao_vien
AS
$$
BEGIN
    RETURN QUERY
        SELECT DISTINCT gv.*
        FROM auth.v_users_giao_vien gv
                 JOIN school.lop l
                      ON l.giao_vien_id = gv.out_id
        WHERE l.truong_id = p_truong_id
          AND (
            p_search IS NULL OR p_search = ''
                OR unaccent(lower(gv.ho_ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
                OR unaccent(lower(gv.username)) LIKE '%' || unaccent(lower(p_search)) || '%'
            )
        ORDER BY gv.ho_ten
        OFFSET p_offset LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
