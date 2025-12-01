CREATE OR REPLACE FUNCTION school.fn_xoa_lop
(
    p_id BIGINT,
    id_nguoi_xoa BIGINT
)
RETURNS BOOLEAN
AS $$
DECLARE
    v_nguoi_tao BIGINT;
    v_la_admin BOOLEAN;
BEGIN
    -- Kiểm tra lớp có tồn tại
    IF NOT EXISTS(SELECT 1 FROM school.lop WHERE id = p_id) THEN
        RAISE EXCEPTION 'Lớp với id % không tồn tại', p_id;
    END IF;

    -- Kiểm tra người xóa có tồn tại
    IF NOT EXISTS(SELECT 1 FROM auth.users WHERE id = id_nguoi_xoa) THEN
        RAISE EXCEPTION 'Người xóa với id % không tồn tại', id_nguoi_xoa;
    END IF;

    -- Người tạo lớp
    SELECT giao_vien_id INTO v_nguoi_tao
    FROM school.lop
    WHERE id = p_id;

    -- Kiểm tra quyền admin
    SELECT (role = 'ADMIN') INTO v_la_admin
    FROM auth.users
    WHERE id = id_nguoi_xoa;

    -- Chỉ admin hoặc người tạo lớp được phép xóa
    IF NOT (v_la_admin OR v_nguoi_tao = id_nguoi_xoa) THEN
        RAISE EXCEPTION
            'Bạn không có quyền xóa lớp % (chỉ admin hoặc người tạo mới được phép)',
            p_id;
    END IF;

    -- Xóa lớp
    DELETE FROM school.lop WHERE id = p_id;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
