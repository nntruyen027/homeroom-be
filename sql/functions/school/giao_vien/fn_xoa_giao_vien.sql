DROP FUNCTION IF EXISTS school.fn_xoa_giao_vien;

CREATE OR REPLACE FUNCTION school.fn_xoa_giao_vien(
    p_id BIGINT
)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_is_teacher BOOLEAN;
    v_has_lop    BOOLEAN;
BEGIN
    -- 1️⃣ Kiểm tra có phải giáo viên không
    SELECT EXISTS (SELECT 1
                   FROM auth.user_roles ur
                            JOIN auth.roles r ON r.id = ur.role_id
                   WHERE ur.user_id = p_id
                     AND r.code = 'TEACHER')
    INTO v_is_teacher;

    IF NOT v_is_teacher THEN
        RETURN FALSE;
    END IF;

    -- 2️⃣ Kiểm tra giáo viên còn dạy lớp nào không
    SELECT EXISTS (SELECT 1
                   FROM school.lop l
                   WHERE l.giao_vien_id = p_id)
    INTO v_has_lop;

    IF v_has_lop THEN
        RAISE EXCEPTION
            'Không thể xoá giáo viên % vì vẫn đang được gán lớp học', p_id;
    END IF;

    -- 3️⃣ Xoá role
    DELETE
    FROM auth.user_roles
    WHERE user_id = p_id;

    -- 4️⃣ Xoá user
    DELETE
    FROM auth.users
    WHERE id = p_id;

    RETURN TRUE;
END;
$$;
