CREATE OR REPLACE FUNCTION school.fn_lay_lop_theo_truong
(
    p_truong_id BIGINT,
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
    -- Kiểm tra trường tồn tại
    IF NOT EXISTS(SELECT 1 FROM school.truong WHERE id = p_truong_id) THEN
        RAISE EXCEPTION 'Trường với id % không tồn tại', p_truong_id;
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
    WHERE l.truong_id = p_truong_id
          p_search IS NULL OR p_search = ''
          OR unaccent(lower(l.ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
    ORDER BY l.id DESC
    OFFSET (p_offset - 1) * p_limit
    LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
