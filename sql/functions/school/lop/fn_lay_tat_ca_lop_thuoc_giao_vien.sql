DROP FUNCTION IF EXISTS school.fn_lay_tat_ca_lop_thuoc_giao_vien;

CREATE OR REPLACE FUNCTION school.fn_lay_tat_ca_lop_thuoc_giao_vien(
    p_giao_vien_id BIGINT,
    p_truong_id BIGINT,
    p_search TEXT DEFAULT NULL,
    p_offset INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
    RETURNS SETOF school.v_lop
AS
$$
BEGIN
    -- Kiểm tra giáo viên tồn tại
    IF NOT EXISTS(SELECT 1 FROM auth.users WHERE id = p_giao_vien_id) THEN
        RAISE EXCEPTION 'Giáo viên với id % không tồn tại', p_giao_vien_id;
    END IF;

    IF p_truong_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM school.truong WHERE id = p_truong_id) THEN
        RAISE EXCEPTION 'Trường với id % không tồn tại', p_truong_id;
    end if;


    RETURN QUERY
        SELECT *
        FROM school.v_lop l
        WHERE l.giao_vien_id = p_giao_vien_id
          AND (
            p_truong_id IS NULL
                OR l.truong_id = p_truong_id
            )
          AND (
            p_search IS NULL OR p_search = ''
                OR unaccent(lower(l.ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
            )
        ORDER BY l.out_id DESC
        OFFSET p_offset * p_limit LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;