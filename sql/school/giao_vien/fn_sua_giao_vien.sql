DROP FUNCTION IF EXISTS school.fn_sua_giao_vien;

CREATE OR REPLACE FUNCTION school.fn_sua_giao_vien
(
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
RETURNS TABLE
(
    out_id BIGINT,
    username VARCHAR(50),
    ho_ten TEXT,
    avatar TEXT,
    ngay_sinh VARCHAR(50),
    la_nam BOOLEAN,
    bo_mon TEXT,
    chuc_vu TEXT,
    dia_chi_chi_tiet VARCHAR(500),
    xa_id BIGINT,
    ten_xa VARCHAR(120),
    tinh_id BIGINT,
    ten_tinh VARCHAR(120),
    role_name VARCHAR(30)
)
AS $$
BEGIN
    -- kiểm tra giáo viên tồn tại (explicitly qualify role)
    IF NOT EXISTS (
        SELECT 1
        FROM auth.users u
        WHERE u.id = p_id
          AND u."role" = 'TEACHER'
    ) THEN
        RAISE EXCEPTION 'Giáo viên với id % không tồn tại', p_id;
    END IF;

    -- kiểm tra xã tồn tại
    IF NOT EXISTS (
        SELECT 1
        FROM dm_chung.xa x
        WHERE x.id = p_xa_id
    ) THEN
        RAISE EXCEPTION 'Xã với id % không tồn tại', p_xa_id;
    END IF;

    -- cập nhật
    UPDATE auth.users
    SET
        avatar = p_avatar,
        ho_ten = p_ho_ten,
        ngay_sinh = p_ngay_sinh,
        la_nam = p_la_nam,
        bo_mon = p_bo_mon,
        chuc_vu = p_chuc_vu,
        dia_chi_chi_tiet = p_dia_chi_chi_tiet,
        xa_id = p_xa_id
    WHERE id = p_id
      AND "role" = 'TEACHER';

    -- trả về thông tin sau cập nhật, trả role cứng là 'TEACHER' dưới alias role_name
    RETURN QUERY
    SELECT
        u.id AS out_id,
        u.username,
        u.ho_ten,
        u.avatar,
        u.ngay_sinh,
        u.la_nam,
        u.bo_mon,
        u.chuc_vu,
        u.dia_chi_chi_tiet,
        x.id AS xa_id,
        x.ten AS ten_xa,
        t.id AS tinh_id,
        t.ten AS ten_tinh,
        u.role AS role_name
    FROM auth.users u
    LEFT JOIN dm_chung.xa x ON x.id = u.xa_id
    LEFT JOIN dm_chung.tinh t ON t.id = x.tinh_id
    WHERE u.id = p_id
      AND u.role = 'TEACHER'
    LIMIT 1;
END;
$$ LANGUAGE plpgsql;
