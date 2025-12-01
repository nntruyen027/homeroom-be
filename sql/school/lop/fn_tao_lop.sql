CREATE OR REPLACE FUNCTION school.fn_tao_lop
(
    p_ten VARCHAR(120),
    p_hinh_anh TEXT,
    p_truong_id BIGINT,
    p_giao_vien_id BIGINT
)
RETURNS TABLE
(
    out_id BIGINT,
    ten VARCHAR(120),
    hinh_anh TEXT,
    truong_id BIGINT,
    ten_truong VARCHAR(120),
    giao_vien_id BIGINT,
    ten_giao_vien TEXT
)
AS $$
DECLARE
    new_id BIGINT;
BEGIN
    IF NOT EXISTS(SELECT 1 FROM school.truong WHERE id = p_truong_id) THEN
        RAISE EXCEPTION 'Trường với id % không tồn tại', p_truong_id;
    END IF;

    IF NOT EXISTS(SELECT 1 FROM auth.users WHERE id = p_giao_vien_id) THEN
        RAISE EXCEPTION 'Giáo viên với id % không tồn tại', p_giao_vien_id;
    END IF;

    INSERT INTO school.lop (
        ten, hinh_anh, truong_id, giao_vien_id
    )
    VALUES (
        p_ten, p_hinh_anh, p_truong_id, p_giao_vien_id
    )
    RETURNING id INTO new_id;

    -- Trả về thông tin lớp
    RETURN QUERY
    SELECT
        l.id AS out_id,
        l.ten,
        l.hinh_anh,
        l.truong_id,
        t.ten AS ten_truong,
        l.giao_vien_id,
        u.full_name AS ten_giao_vien
    FROM school.lop l
    LEFT JOIN school.truong t ON t.id = l.truong_id
    LEFT JOIN auth.users u ON u.id = l.giao_vien_id
    WHERE l.id = new_id;
END;
$$ LANGUAGE plpgsql;
