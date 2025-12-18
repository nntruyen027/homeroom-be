DROP FUNCTION IF EXISTS school.fn_nhan_xet_log_hd_huong_nghiep;

CREATE OR REPLACE FUNCTION school.fn_nhan_xet_log_hd_huong_nghiep(
    p_user_id bigint,
    p_hd_id bigint,
    p_nhan_xet varchar(500),
    p_gv_id bigint
)
    RETURNS SETOF school.v_log_hoat_dong_huong_nghiep
    LANGUAGE plpgsql
AS
$$
BEGIN

    -- 3️⃣ Cập nhật log (không thay đổi user_id và hd_id)
    UPDATE school.hs_hoat_dong_huong_nghiep
    SET nhan_xet = p_nhan_xet,
        gv_id    = p_gv_id
    WHERE user_id = p_user_id
      AND hd_id = p_hd_id;

    RETURN QUERY
        SELECT *
        FROM school.v_log_hoat_dong_huong_nghiep
        WHERE id_hoat_dong = p_hd_id
          AND id_hoc_sinh = p_user_id
        LIMIT 1;
END;
$$;
