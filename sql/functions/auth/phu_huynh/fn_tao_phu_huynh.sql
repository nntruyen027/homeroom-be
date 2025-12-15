DROP FUNCTION IF EXISTS auth.fn_tao_phu_huynh;
CREATE FUNCTION auth.fn_tao_phu_huynh(
    p_username VARCHAR(120),
    p_password VARCHAR(500),
    p_ho_ten VARCHAR(500),
    p_hoc_sinh_id BIGINT,
    p_loai_phu_huynh VARCHAR(50),
    p_so_dien_thoai VARCHAR(11),
    p_ngay_sinh DATE,
    p_la_nam BOOLEAN,
    p_xa_id BIGINT,
    p_dia_chi VARCHAR(500)
)
    RETURNS SETOF auth.v_phu_huynh_full
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

    -- 3. Phụ huynh
    INSERT INTO auth.phu_huynh(user_id,
                               hoc_sinh_id,
                               loai_phu_huynh,
                               so_dien_thoai)
    VALUES (v_user_id,
            p_hoc_sinh_id,
            p_loai_phu_huynh,
            p_so_dien_thoai);

    -- 4. Lấy role PARENT
    SELECT id
    INTO v_role_id
    FROM auth.roles
    WHERE code = 'PARENT';

    IF v_role_id IS NULL THEN
        RAISE EXCEPTION 'Role PARENT chưa tồn tại';
    END IF;

    -- 5. Gán role PARENT
    INSERT INTO auth.user_roles(user_id, role_id)
    VALUES (v_user_id, v_role_id);

    RETURN QUERY SELECT *
                 FROM auth.v_phu_huynh_full
                 WHERE user_id = v_user_id
                 LIMIT 1;
END;
$$;
