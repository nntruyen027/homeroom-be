-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_sua_vai_tro;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_sua_vai_tro(
    p_id BIGINT, -- ID của vai trò cần sửa
    p_name VARCHAR(500), -- Tên mới
    p_code VARCHAR(50) -- Mã mới
)
    RETURNS SETOF auth.v_role
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- Kiểm tra role có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM auth.roles WHERE id = p_id) THEN
        RAISE EXCEPTION 'Vai trò với ID % không tồn tại', p_id;
    END IF;

    -- Kiểm tra code mới đã trùng với role khác chưa
    IF EXISTS (SELECT 1 FROM auth.roles WHERE code = p_code AND id <> p_id) THEN
        RAISE EXCEPTION 'Mã role % đã tồn tại cho role khác', p_code;
    END IF;

    -- Cập nhật role
    UPDATE auth.roles
    SET name = p_name,
        code = p_code
    WHERE id = p_id;

    -- Trả về record vừa cập nhật
    RETURN QUERY
        SELECT *
        FROM auth.v_role
        WHERE out_id = p_id;

END;
$$;
