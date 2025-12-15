DROP FUNCTION IF EXISTS auth.fn_cap_nhat_giao_vien;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_giao_vien(
    p_user_id BIGINT,
    p_bo_mon TEXT,
    p_chuc_vu TEXT
)
    RETURNS SETOF auth.v_giao_vien_full
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM auth.giao_vien WHERE user_id = p_user_id) THEN
        RAISE EXCEPTION 'Giáo viên user_id % không tồn tại', p_user_id;
    END IF;

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
