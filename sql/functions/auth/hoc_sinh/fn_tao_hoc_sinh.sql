DROP FUNCTION IF EXISTS auth.fn_tao_hoc_sinh;
CREATE FUNCTION auth.fn_tao_hoc_sinh(
    p_username VARCHAR(120),
    p_password VARCHAR(500),
    p_ho_ten VARCHAR(500),
    p_lop_id BIGINT,
    p_ngay_sinh DATE,
    p_la_nam BOOLEAN,
    p_xa_id BIGINT,
    p_dia_chi VARCHAR(500)
)
    RETURNS SETOF auth.v_hoc_sinh_full
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

    -- 2. Tạo profile
    PERFORM auth.fn_tao_thong_tin_nguoi_dung(
            v_user_id, p_ngay_sinh, p_la_nam, p_xa_id, p_dia_chi
            );

    -- 3. Tạo học sinh
    INSERT INTO auth.hoc_sinh(user_id, lop_id)
    VALUES (v_user_id, p_lop_id);

    -- 4. Lấy role STUDENT
    SELECT id
    INTO v_role_id
    FROM auth.roles
    WHERE code = 'STUDENT';

    IF v_role_id IS NULL THEN
        RAISE EXCEPTION 'Role STUDENT chưa tồn tại';
    END IF;

    -- 5. Gán role STUDENT cho user
    INSERT INTO auth.user_roles(user_id, role_id)
    VALUES (v_user_id, v_role_id);

    RETURN QUERY SELECT *
                 FROM auth.v_hoc_sinh_full
                 WHERE user_id = v_user_id
                 LIMIT 1;
END;
$$;
