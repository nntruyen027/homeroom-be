-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_phan_quyen_cho_vai_tro;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_phan_quyen_cho_vai_tro(
    p_role_id BIGINT,
    p_permission_codes TEXT[]
)
    RETURNS SETOF auth.v_role
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_permission_id BIGINT;
    v_code          TEXT;
BEGIN
    -- Kiểm tra role tồn tại
    IF NOT EXISTS (SELECT 1 FROM auth.roles WHERE id = p_role_id) THEN
        RAISE EXCEPTION 'Vai trò với ID % không tồn tại', p_role_id;
    END IF;

    -- 1️⃣ Xóa những quyền hiện có mà không nằm trong danh sách mới
    DELETE
    FROM auth.role_permissions
    WHERE role_id = p_role_id
      AND permission_id NOT IN (SELECT id
                                FROM auth.permissions
                                WHERE code = ANY (p_permission_codes));

    -- 2️⃣ Duyệt từng permission code trong danh sách mới để thêm nếu chưa có
    FOREACH v_code IN ARRAY p_permission_codes
        LOOP
            -- Kiểm tra permission tồn tại
            SELECT id
            INTO v_permission_id
            FROM auth.permissions
            WHERE code = v_code;

            IF v_permission_id IS NULL THEN
                RAISE EXCEPTION 'Permission với mã % không tồn tại', v_code;
            END IF;

            -- Thêm quyền nếu chưa tồn tại
            IF NOT EXISTS (SELECT 1
                           FROM auth.role_permissions
                           WHERE role_id = p_role_id
                             AND permission_id = v_permission_id) THEN
                INSERT INTO auth.role_permissions(role_id, permission_id)
                VALUES (p_role_id, v_permission_id);
            END IF;
        END LOOP;

    -- Trả về thông tin role
    RETURN QUERY SELECT * FROM auth.v_role v WHERE v.out_id = p_role_id;
END;
$$;
