-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_co_ton_tai_vai_tro_theo_ma;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_co_ton_tai_vai_tro_theo_ma(
    p_code VARCHAR(50)
)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- Kiểm tra nếu tồn tại vai trò với code tương ứng
    IF EXISTS (SELECT 1
               FROM auth.roles
               WHERE code = p_code) THEN
        RETURN TRUE;
    END IF;

    RETURN FALSE;
END;
$$;
