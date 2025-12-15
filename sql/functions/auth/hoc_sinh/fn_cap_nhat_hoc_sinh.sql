DROP FUNCTION IF EXISTS auth.fn_cap_nhat_hoc_sinh;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_hoc_sinh(
    p_user_id BIGINT,
    p_lop_id BIGINT,
    p_so_thich VARCHAR(500),
    p_mon_hoc_yeu_thich VARCHAR(500),
    p_diem_manh VARCHAR(500),
    p_diem_yeu VARCHAR(500),
    p_ghi_chu VARCHAR(500)
)
    RETURNS SETOF auth.v_hoc_sinh_full
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM auth.hoc_sinh WHERE user_id = p_user_id) THEN
        RAISE EXCEPTION 'Học sinh user_id % không tồn tại', p_user_id;
    END IF;

    UPDATE auth.hoc_sinh
    SET lop_id            = p_lop_id,
        so_thich          = p_so_thich,
        mon_hoc_yeu_thich = p_mon_hoc_yeu_thich,
        diem_manh         = p_diem_manh,
        diem_yeu          = p_diem_yeu,
        ghi_chu           = p_ghi_chu
    WHERE user_id = p_user_id;

    RETURN QUERY
        SELECT *
        FROM auth.v_hoc_sinh_full
        WHERE user_id = p_user_id;
END;
$$;
