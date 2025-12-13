DROP FUNCTION IF EXISTS school.fn_tao_giao_vien;

CREATE OR REPLACE FUNCTION school.fn_tao_giao_vien(
    p_username VARCHAR(120),
    p_password TEXT,
    p_avatar TEXT,
    p_ho_ten TEXT,
    p_ngay_sinh VARCHAR(50),
    p_la_nam BOOLEAN,
    p_xa_id BIGINT,
    p_dia_chi_chi_tiet VARCHAR(500),
    p_bo_mon TEXT,
    p_chuc_vu TEXT
)
    RETURNS SETOF auth.v_users_giao_vien
AS
$$
DECLARE
    new_id    BIGINT;
    v_role_id BIGINT;
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM dm_chung.xa x
                   WHERE x.id = p_xa_id) THEN
        RAISE EXCEPTION 'Xã với id % không tồn tại', p_xa_id;
    END IF;

    IF EXISTS (SELECT 1
               FROM auth.users u
               WHERE u.username = p_username) THEN
        RAISE EXCEPTION 'Username % đã tồn tại', p_username;
    END IF;

    SELECT r.id
    INTO v_role_id
    FROM auth.roles r
    WHERE r.code = 'TEACHER';

    IF v_role_id IS NULL THEN
        RAISE EXCEPTION 'Role TEACHER chưa được khởi tạo';
    END IF;

    INSERT INTO auth.users (username,
                            password,
                            avatar,
                            ho_ten,
                            ngay_sinh,
                            la_nam,
                            bo_mon,
                            chuc_vu,
                            xa_id,
                            dia_chi_chi_tiet)
    VALUES (p_username,
            p_password,
            p_avatar,
            p_ho_ten,
            p_ngay_sinh,
            p_la_nam,
            p_bo_mon,
            p_chuc_vu,
            p_xa_id,
            p_dia_chi_chi_tiet)
    RETURNING id INTO new_id;

    INSERT INTO auth.user_roles (user_id, role_id)
    VALUES (new_id, v_role_id);

    RETURN QUERY
        SELECT *
        FROM auth.v_users_giao_vien
        WHERE out_id = new_id
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
