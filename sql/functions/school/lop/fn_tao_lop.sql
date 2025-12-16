DROP FUNCTION IF EXISTS school.fn_tao_lop;

CREATE OR REPLACE FUNCTION school.fn_tao_lop(
    p_ten VARCHAR(120),
    p_hinh_anh TEXT,
    p_truong_id BIGINT,
    p_nguoi_tao BIGINT
)
    RETURNS SETOF school.v_lop
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_new_id    BIGINT;
    v_duoc_phep BOOLEAN;
BEGIN
    -- 1️⃣ Kiểm tra tồn tại trường
    IF NOT EXISTS (SELECT 1
                   FROM school.truong
                   WHERE id = p_truong_id) THEN
        RAISE EXCEPTION 'Trường với id % không tồn tại', p_truong_id;
    END IF;

    IF NOT EXISTS(SELECT 1 FROM auth.users WHERE id = p_nguoi_tao) THEN
        RAISE EXCEPTION
            'Không tồn tại user với id %', p_nguoi_tao;
    end if;

    -- 2️⃣ Kiểm tra quyền ADMIN hoặc TEACHER
    SELECT EXISTS (SELECT 1
                   FROM auth.user_roles ur
                            JOIN auth.roles r ON r.id = ur.role_id
                   WHERE ur.user_id = p_nguoi_tao
                     AND r.code IN ('ADMIN', 'TEACHER'))
    INTO v_duoc_phep;

    IF NOT v_duoc_phep THEN
        RAISE EXCEPTION
            'Bạn không có quyền tạo lớp (chỉ ADMIN hoặc GIÁO VIÊN)';
    END IF;


    -- 3️⃣ Tạo lớp
    INSERT INTO school.lop (ten,
                            hinh_anh,
                            truong_id,
                            giao_vien_id)
    VALUES (p_ten,
            p_hinh_anh,
            p_truong_id,
            p_nguoi_tao)
    RETURNING id INTO v_new_id;

    -- 4️⃣ Trả về lớp vừa tạo
    RETURN QUERY
        SELECT *
        FROM school.v_lop l
        WHERE l.out_id = v_new_id
        LIMIT 1;
END;
$$;
