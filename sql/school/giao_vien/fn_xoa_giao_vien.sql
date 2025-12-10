DROP FUNCTION IF EXISTS school.fn_xoa_giao_vien;

CREATE OR REPLACE FUNCTION school.fn_xoa_giao_vien(
    p_id BIGINT
)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
DECLARE
    v_exists BOOLEAN;
BEGIN
    -- Kiểm tra xem giáo viên có tồn tại hay không
    SELECT EXISTS(
        SELECT 1 FROM auth.users
        WHERE id = p_id AND role ILIKE '%TEACHER%'
    ) INTO v_exists;

    IF NOT v_exists THEN
        RETURN FALSE; -- Không tồn tại → không xoá
    END IF;

    -- Xoá
    DELETE FROM auth.users WHERE id = p_id;

    RETURN TRUE;
END;
$$;
