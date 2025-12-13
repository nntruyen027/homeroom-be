DROP FUNCTION IF EXISTS school.fn_sua_lop;

CREATE OR REPLACE FUNCTION school.fn_sua_lop(
    p_id BIGINT,
    p_ten VARCHAR(120),
    p_hinh_anh TEXT,
    p_truong_id BIGINT,
    p_nguoi_sua BIGINT
)
    RETURNS SETOF school.v_lop
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_nguoi_tao BIGINT;
    v_la_admin  BOOLEAN;
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM school.lop
                   WHERE id = p_id) THEN
        RAISE EXCEPTION 'Lớp với id % không tồn tại', p_id;
    END IF;

    IF NOT EXISTS (SELECT 1
                   FROM school.truong
                   WHERE id = p_truong_id) THEN
        RAISE EXCEPTION 'Trường với id % không tồn tại', p_truong_id;
    END IF;

    SELECT giao_vien_id
    INTO v_nguoi_tao
    FROM school.lop
    WHERE id = p_id;
    
    SELECT EXISTS (SELECT 1
                   FROM auth.user_roles ur
                            JOIN auth.roles r ON r.id = ur.role_id
                   WHERE ur.user_id = p_nguoi_sua
                     AND r.code = 'ADMIN')
    INTO v_la_admin;

    -- 5️⃣ Check quyền sửa
    IF NOT (v_la_admin OR v_nguoi_tao = p_nguoi_sua) THEN
        RAISE EXCEPTION
            'Bạn không có quyền sửa lớp % (chỉ ADMIN hoặc giáo viên phụ trách)', p_id;
    END IF;

    -- 6️⃣ Cập nhật lớp
    UPDATE school.lop
    SET ten       = p_ten,
        hinh_anh  = p_hinh_anh,
        truong_id = p_truong_id
    WHERE id = p_id;

    -- 7️⃣ Trả về dữ liệu sau cập nhật
    RETURN QUERY
        SELECT *
        FROM school.v_lop l
        WHERE l.out_id = p_id;
END;
$$;
