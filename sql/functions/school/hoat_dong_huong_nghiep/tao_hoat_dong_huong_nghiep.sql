DROP FUNCTION IF EXISTS school.fn_tao_hoat_dong_huong_nghiep;

CREATE OR REPLACE FUNCTION school.fn_tao_hoat_dong_huong_nghiep(
    p_ten TEXT,
    p_thoi_gian_bat_dau DATE,
    p_thoi_gian_ket_thuc DATE,
    p_ghi_chu TEXT,
    p_nguoi_tao_id BIGINT
)
    RETURNS SETOF school.v_hoat_dong_huong_nghiep
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_id BIGINT;
BEGIN
    INSERT INTO school.hoat_dong_huong_nghiep(ten,
                                              thoi_gian_bat_dau,
                                              thoi_gian_ket_thuc,
                                              ghi_chu,
                                              nguoi_tao_id)
    VALUES (p_ten,
            p_thoi_gian_bat_dau,
            p_thoi_gian_ket_thuc,
            p_ghi_chu,
            p_nguoi_tao_id)
    RETURNING id INTO v_id;

    RETURN QUERY SELECT * FROM school.v_hoat_dong_huong_nghiep WHERE hoat_dong_id = v_id limit 1;
END;
$$;
