CREATE OR REPLACE FUNCTION school.fn_lay_tat_ca_lop_thuoc_giao_vien
(
    p_giao_vien_id BIGINT,
    p_search TEXT DEFAULT NULL,
    p_offset INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
RETURNS TABLE
(
    out_id BIGINT,
    ten VARCHAR(120),
    hinh_anh TEXT,
    giao_vien_id BIGINT,
    ten_giao_vien TEXT,
    truong_id BIGINT,
    ten_truong TEXT
)
AS $$
BEGIN
    -- Kiểm tra giáo viên tồn tại
    IF NOT EXISTS(SELECT 1 FROM auth.users WHERE id = p_giao_vien_id) THEN
        RAISE EXCEPTION 'Giáo viên với id % không tồn tại', p_giao_vien_id;
    END IF;

    RETURN QUERY
    SELECT
        l.id AS out_id,
        l.ten,
        l.hinh_anh,
        l.giao_vien_id,
        u.full_name AS ten_giao_vien,
        l.truong_id,
        t.ten AS ten_truong
    FROM school.lop l
    LEFT JOIN auth.users u ON u.id = l.giao_vien_id
    LEFT JOIN school.truong t ON t.id = l.truong_id
    WHERE l.giao_vien_id = p_giao_vien_id
      AND (
            p_search IS NULL OR p_search = ''
            OR unaccent(lower(l.ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
          )
    ORDER BY l.id DESC
    OFFSET (p_offset - 1) * p_limit
    LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
