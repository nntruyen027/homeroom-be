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

    -- Duyệt từng role code
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

            -- Kiểm tra xem role đã gán cho user chưa
            IF NOT EXISTS (SELECT 1
                           FROM auth.user_roles
                           WHERE user_id = p_user_id
                             AND role_id = v_role_id) THEN
                INSERT INTO auth.user_roles(user_id, role_id)
                VALUES (p_user_id, v_role_id);
            END IF;
        END LOOP;

    RETURN QUERY SELECT * FROM auth.v_users_full WHERE out_id = p_user_id LIMIT 1;
END;
$$;
