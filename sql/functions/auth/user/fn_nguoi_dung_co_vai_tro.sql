-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_nguoi_dung_co_vai_tro;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_nguoi_dung_co_vai_tro(
    p_user_id BIGINT, -- ID người dùng cần kiểm tra
    p_role_code VARCHAR(50) -- Mã vai trò, ví dụ 'ADMIN'
)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN EXISTS (SELECT 1
                   FROM auth.user_roles ur
                            JOIN auth.roles r ON ur.role_id = r.id
                   WHERE ur.user_id = p_user_id
                     AND r.code = p_role_code);
END;
$$;
