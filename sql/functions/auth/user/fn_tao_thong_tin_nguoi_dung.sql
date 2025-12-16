DROP FUNCTION IF EXISTS auth.fn_tao_thong_tin_nguoi_dung;
CREATE FUNCTION auth.fn_tao_thong_tin_nguoi_dung(
    p_user_id BIGINT,
    p_ngay_sinh DATE,
    p_la_nam BOOLEAN,
    p_xa_id BIGINT,
    p_dia_chi VARCHAR(500)
)
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS(SELECT 1 FROM dm_chung.xa WHERE id = p_xa_id) THEN
        RAISE EXCEPTION 'Xã với id % không tồn tại', p_xa_id;
    end if;

    INSERT INTO auth.thong_tin_nguoi_dung(user_id, ngay_sinh, la_nam, xa_id, dia_chi_chi_tiet)
    VALUES (p_user_id, p_ngay_sinh, p_la_nam, p_xa_id, p_dia_chi);
END;
$$;
