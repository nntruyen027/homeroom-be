-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_phan_vai_tro_cho_nguoi_dung;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_phan_vai_tro_cho_nguoi_dung(
    p_user_id BIGINT,
    p_role_codes TEXT[]
)
    RETURNS SETOF auth.v_users_full
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_role_id BIGINT;
    v_code    TEXT;
BEGIN
    -- Kiểm tra user tồn tại
    IF NOT EXISTS (SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'Người dùng với ID % không tồn tại', p_user_id;
    END IF;

    -- 1️⃣ Xóa các role hiện có mà không nằm trong danh sách mới
    DELETE
    FROM auth.user_roles
    WHERE user_id = p_user_id
      AND role_id NOT IN (SELECT id
                          FROM auth.roles
                          WHERE code = ANY (p_role_codes));

    -- 2️⃣ Duyệt từng role code trong danh sách mới để thêm nếu chưa có
    FOREACH v_code IN ARRAY p_role_codes
        LOOP
            -- Kiểm tra role tồn tại
            SELECT id
            INTO v_role_id
            FROM auth.roles
            WHERE code = v_code;

            IF v_role_id IS NULL THEN
                RAISE EXCEPTION 'Role với mã % không tồn tại', v_code;
            END IF;

            -- Thêm role nếu chưa gán cho user
            IF NOT EXISTS (SELECT 1
                           FROM auth.user_roles
                           WHERE user_id = p_user_id
                             AND role_id = v_role_id) THEN
                INSERT INTO auth.user_roles(user_id, role_id)
                VALUES (p_user_id, v_role_id);
            END IF;
        END LOOP;

    -- Trả về thông tin user
    RETURN QUERY SELECT * FROM auth.v_users_full WHERE id = p_user_id LIMIT 1;
END;
$$;
