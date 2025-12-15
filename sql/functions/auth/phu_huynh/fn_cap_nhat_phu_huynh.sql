DROP FUNCTION IF EXISTS auth.fn_cap_nhat_phu_huynh;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_phu_huynh(
    p_user_id BIGINT,
    p_hoc_sinh_id BIGINT,
    p_loai_phu_huynh VARCHAR(50),
    p_so_dien_thoai VARCHAR(20)
)
    RETURNS SETOF auth.v_phu_huynh_full
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM auth.phu_huynh WHERE user_id = p_user_id) THEN
        RAISE EXCEPTION 'Phụ huynh user_id % không tồn tại', p_user_id;
    END IF;

    UPDATE auth.phu_huynh
    SET hoc_sinh_id    = p_hoc_sinh_id,
        loai_phu_huynh = p_loai_phu_huynh,
        so_dien_thoai  = p_so_dien_thoai
    WHERE user_id = p_user_id;

    RETURN QUERY
        SELECT *
        FROM auth.v_phu_huynh_full
        WHERE user_id = p_user_id;
END;
$$;
