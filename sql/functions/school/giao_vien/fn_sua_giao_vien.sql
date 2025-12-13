DROP FUNCTION IF EXISTS school.fn_sua_giao_vien;

CREATE OR REPLACE FUNCTION school.fn_sua_giao_vien(
    p_id BIGINT,
    p_avatar TEXT,
    p_ho_ten TEXT,
    p_ngay_sinh VARCHAR(50),
    p_la_nam BOOLEAN,
    p_xa_id BIGINT,
    p_dia_chi_chi_tiet VARCHAR(120),
    p_bo_mon TEXT,
    p_chuc_vu TEXT
)
    RETURNS SETOF auth.v_users_giao_vien
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM auth.v_users_giao_vien u
                   WHERE u.out_id = p_id) THEN
        RAISE EXCEPTION 'Giáo viên với id % không tồn tại', p_id;
    END IF;

    -- kiểm tra xã tồn tại
    IF NOT EXISTS (SELECT 1
                   FROM dm_chung.xa x
                   WHERE x.id = p_xa_id) THEN
        RAISE EXCEPTION 'Xã với id % không tồn tại', p_xa_id;
    END IF;

    -- cập nhật
    UPDATE auth.users
    SET avatar           = p_avatar,
        ho_ten           = p_ho_ten,
        ngay_sinh        = p_ngay_sinh,
        la_nam           = p_la_nam,
        bo_mon           = p_bo_mon,
        chuc_vu          = p_chuc_vu,
        dia_chi_chi_tiet = p_dia_chi_chi_tiet,
        xa_id            = p_xa_id
    WHERE id = p_id;

    -- trả về thông tin sau cập nhật, trả role cứng là 'TEACHER' dưới alias role_name
    RETURN QUERY
        SELECT*
        FROM auth.v_users_giao_vien u
        WHERE u.out_id = p_id
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
