DROP FUNCTION IF EXISTS school.fn_xoa_lop;

CREATE OR REPLACE FUNCTION school.fn_xoa_lop(
    p_id BIGINT,
    p_nguoi_xoa BIGINT
)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_nguoi_tao BIGINT;
    v_la_admin  BOOLEAN;
BEGIN
    -- 1️⃣ Kiểm tra lớp có tồn tại
    IF NOT EXISTS (SELECT 1
                   FROM school.lop
                   WHERE id = p_id) THEN
        RAISE EXCEPTION 'Lớp với id % không tồn tại', p_id;
    END IF;

    -- 2️⃣ Kiểm tra người xóa có tồn tại
    IF NOT EXISTS (SELECT 1
                   FROM auth.users
                   WHERE id = p_nguoi_xoa) THEN
        RAISE EXCEPTION 'Người xóa với id % không tồn tại', p_nguoi_xoa;
    END IF;

    -- 3️⃣ Lấy người tạo lớp
    SELECT giao_vien_id
    INTO v_nguoi_tao
    FROM school.lop
    WHERE id = p_id;

    -- 4️⃣ Kiểm tra quyền ADMIN (RBAC)
    SELECT EXISTS (SELECT 1
                   FROM auth.user_roles ur
                            JOIN auth.roles r ON r.id = ur.role_id
                   WHERE ur.user_id = p_nguoi_xoa
                     AND r.code = 'ADMIN')
    INTO v_la_admin;

    -- 5️⃣ Chỉ ADMIN hoặc người tạo lớp mới được xóa
    IF NOT (v_la_admin OR v_nguoi_tao = p_nguoi_xoa) THEN
        RAISE EXCEPTION
            'Bạn không có quyền xóa lớp % (chỉ ADMIN hoặc người tạo lớp được phép)',
            p_id;
    END IF;

    -- 6️⃣ Xóa lớp
    DELETE FROM school.lop WHERE id = p_id;

    RETURN TRUE;
END;
$$;
