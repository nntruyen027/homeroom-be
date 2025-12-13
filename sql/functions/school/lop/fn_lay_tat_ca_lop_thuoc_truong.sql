DROP FUNCTION IF EXISTS school.fn_lay_tat_ca_lop_thuoc_truong;

CREATE OR REPLACE FUNCTION school.fn_lay_tat_ca_lop_thuoc_truong(
    p_truong_id BIGINT,
    p_search TEXT DEFAULT NULL,
    p_offset INT DEFAULT 0,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF school.v_lop
AS
$$
BEGIN
    -- Kiểm tra trường tồn tại
    IF NOT EXISTS(SELECT 1 FROM school.truong WHERE id = p_truong_id) THEN
        RAISE EXCEPTION 'Trường với id % không tồn tại', p_truong_id;
    END IF;

    RETURN QUERY
        SELECT *
        FROM school.v_lop l
        WHERE l.truong_id = p_truong_id
          AND (p_search IS NULL OR p_search = ''
            OR unaccent(lower(l.ten)) LIKE '%' || unaccent(lower(p_search)) || '%')
        ORDER BY l.out_id DESC
        OFFSET p_offset LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
