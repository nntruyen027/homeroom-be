CREATE OR REPLACE FUNCTION school.fn_sua_lop
(
    p_id BIGINT,
    p_ten VARCHAR(120),
    p_hinh_anh TEXT,
    p_truong_id BIGINT,
    p_nguoi_sua BIGINT
)
RETURNS JSON
AS $$
DECLARE
    v_nguoi_tao BIGINT;
    v_la_admin BOOLEAN;
    v_json JSON;
BEGIN
    -- Kiểm tra tồn tại lớp
    IF NOT EXISTS(SELECT 1 FROM school.lop WHERE id = p_id) THEN
        RAISE EXCEPTION 'Lớp với id % không tồn tại', p_id;
    END IF;

    -- Kiểm tra tồn tại trường
    IF NOT EXISTS(SELECT 1 FROM school.truong WHERE id = p_truong_id) THEN
        RAISE EXCEPTION 'Trường với id % không tồn tại', p_truong_id;
    END IF;

    -- Lấy người tạo lớp
    SELECT giao_vien_id INTO v_nguoi_tao
    FROM school.lop
    WHERE id = p_id;

    -- Kiểm tra quyền admin
    SELECT role = 'ADMIN' INTO v_la_admin
    FROM auth.users
    WHERE id = p_nguoi_sua;

    IF NOT (v_la_admin OR v_nguoi_tao = p_nguoi_sua) THEN
        RAISE EXCEPTION
            'Bạn không có quyền sửa lớp % (chỉ admin hoặc người tạo mới được phép)', p_id;
    END IF;

    -- Cập nhật lớp
    UPDATE school.lop
    SET
        ten = p_ten,
        hinh_anh = p_hinh_anh,
        truong_id = p_truong_id
    WHERE id = p_id;

    -- Trả về JSON
    SELECT row_to_json(t) INTO v_json
    FROM (
        SELECT
            l.id AS out_id,
            l.ten,
            l.hinh_anh,
            l.truong_id,
            t.ten AS ten_truong,
            l.giao_vien_id,
            u.ho_ten AS ten_giao_vien
        FROM school.lop l
        LEFT JOIN school.truong t ON t.id = l.truong_id
        LEFT JOIN auth.users u ON u.id = l.giao_vien_id
        WHERE l.id = p_id
    ) t;

    RETURN v_json;

END;
$$ LANGUAGE plpgsql;
