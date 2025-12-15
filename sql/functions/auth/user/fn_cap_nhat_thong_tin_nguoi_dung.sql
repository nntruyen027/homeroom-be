DROP FUNCTION IF EXISTS auth.fn_cap_nhat_thong_tin_nguoi_dung;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_thong_tin_nguoi_dung(
    p_user_id BIGINT,
    p_ngay_sinh DATE,
    p_la_nam BOOLEAN,
    p_xa_id BIGINT,
    p_dia_chi VARCHAR(500)
)
    RETURNS SETOF auth.v_users_full
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF EXISTS (SELECT 1 FROM auth.thong_tin_nguoi_dung WHERE user_id = p_user_id) THEN
        UPDATE auth.thong_tin_nguoi_dung
        SET ngay_sinh        = p_ngay_sinh,
            la_nam           = p_la_nam,
            xa_id            = p_xa_id,
            dia_chi_chi_tiet = p_dia_chi
        WHERE user_id = p_user_id;
    ELSE
        INSERT INTO auth.thong_tin_nguoi_dung(user_id, ngay_sinh, la_nam, xa_id, dia_chi_chi_tiet)
        VALUES (p_user_id, p_ngay_sinh, p_la_nam, p_xa_id, p_dia_chi);
    END IF;

    RETURN QUERY
        SELECT *
        FROM auth.v_users_full
        WHERE id = p_user_id;
END;
$$;
