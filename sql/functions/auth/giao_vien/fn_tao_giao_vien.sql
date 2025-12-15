DROP FUNCTION IF EXISTS auth.fn_tao_giao_vien;
CREATE FUNCTION auth.fn_tao_giao_vien(
    p_username VARCHAR(120),
    p_password VARCHAR(500),
    p_ho_ten VARCHAR(500),
    p_bo_mon VARCHAR(500),
    p_chuc_vu VARCHAR(500),
    p_ngay_sinh DATE,
    p_la_nam BOOLEAN,
    p_xa_id BIGINT,
    p_dia_chi VARCHAR(500)
)
    RETURNS SETOF auth.v_giao_vien_full
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_user_id BIGINT;
    v_role_id BIGINT;
BEGIN
    -- 1. Tạo user
    v_user_id := auth.fn_tao_nguoi_dung(
            p_username, p_password, p_ho_ten, NULL
                 );

    -- 2. Profile
    PERFORM auth.fn_tao_thong_tin_nguoi_dung(
            v_user_id, p_ngay_sinh, p_la_nam, p_xa_id, p_dia_chi
            );

    -- 3. Giáo viên
    INSERT INTO auth.giao_vien(user_id, bo_mon, chuc_vu)
    VALUES (v_user_id, p_bo_mon, p_chuc_vu);

    -- 4. Lấy role TEACHER
    SELECT id
    INTO v_role_id
    FROM auth.roles
    WHERE code = 'TEACHER';

    IF v_role_id IS NULL THEN
        RAISE EXCEPTION 'Role TEACHER chưa tồn tại';
    END IF;

    -- 5. Gán role TEACHER
    INSERT INTO auth.user_roles(user_id, role_id)
    VALUES (v_user_id, v_role_id);

    RETURN QUERY SELECT * FROM auth.v_giao_vien_full WHERE user_id = v_user_id LIMIT 1;
END;
$$;
