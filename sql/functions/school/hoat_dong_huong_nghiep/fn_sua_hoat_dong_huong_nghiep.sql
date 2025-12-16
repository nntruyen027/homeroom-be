DROP FUNCTION IF EXISTS school.fn_sua_hoat_dong_huong_nghiep;

CREATE OR REPLACE FUNCTION school.fn_sua_hoat_dong_huong_nghiep(
    p_hoat_dong_id BIGINT,
    p_ten TEXT,
    p_thoi_gian_bat_dau DATE,
    p_thoi_gian_ket_thuc DATE,
    p_ghi_chu TEXT
)
    RETURNS SETOF school.v_hoat_dong_huong_nghiep
    LANGUAGE plpgsql
AS
$$
BEGIN
    UPDATE school.hoat_dong_huong_nghiep
    SET ten                = p_ten,
        thoi_gian_bat_dau  = p_thoi_gian_bat_dau,
        thoi_gian_ket_thuc = p_thoi_gian_ket_thuc,
        ghi_chu            = p_ghi_chu
    WHERE id = p_hoat_dong_id;

    RETURN QUERY SELECT * FROM school.v_hoat_dong_huong_nghiep WHERE hoat_dong_id = p_hoat_dong_id LIMIT 1;
END;
$$;
