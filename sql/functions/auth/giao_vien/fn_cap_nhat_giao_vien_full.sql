DROP FUNCTION IF EXISTS auth.fn_cap_nhat_giao_vien_full;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_giao_vien_full(
    p_user_id BIGINT,
    p_ho_ten VARCHAR(500),
    p_avatar VARCHAR(500),
    p_ngay_sinh date,
    p_xa_id bigint,
    p_dia_chi_chi_tiet varchar(500),
    p_la_nam boolean,
    p_bo_mon VARCHAR(500),
    p_chuc_vu VARCHAR(500)
)
    RETURNS SETOF auth.v_giao_vien_full
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM auth.giao_vien WHERE user_id = p_user_id) THEN
        RAISE EXCEPTION 'Giáo viên user_id % không tồn tại', p_user_id;
    END IF;

    perform auth.fn_cap_nhat_thong_tin_nguoi_dung(
            p_user_id := p_user_id,
            p_xa_id := p_xa_id,
            p_la_nam := p_la_nam,
            p_ngay_sinh := p_ngay_sinh,
            p_dia_chi := p_dia_chi_chi_tiet
            );

    perform auth.fn_cap_nhat_nguoi_dung(
            p_user_id := p_user_id,
            p_ho_ten := p_ho_ten,
            p_avatar := p_avatar
            );

    UPDATE auth.giao_vien
    SET bo_mon  = p_bo_mon,
        chuc_vu = p_chuc_vu
    WHERE user_id = p_user_id;

    RETURN QUERY
        SELECT *
        FROM auth.v_giao_vien_full
        WHERE user_id = p_user_id;
END;
$$;
