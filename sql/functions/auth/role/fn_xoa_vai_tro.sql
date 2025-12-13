-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_xoa_vai_tro;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_xoa_vai_tro(
    p_id BIGINT -- ID của role cần xóa
)
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count BIGINT;
BEGIN
    -- Kiểm tra role có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM auth.roles WHERE id = p_id) THEN
        RAISE EXCEPTION 'Vai trò với ID % không tồn tại', p_id;
    END IF;

    -- Kiểm tra role đã được gán cho user chưa
    SELECT COUNT(*)
    INTO v_count
    FROM auth.user_roles
    WHERE role_id = p_id;

    IF v_count > 0 THEN
        RAISE EXCEPTION 'Vai trò đang được sử dụng bởi % người dùng, không thể xóa', v_count;
    END IF;

    -- Xóa role
    DELETE FROM auth.roles WHERE id = p_id;
END;
$$;
