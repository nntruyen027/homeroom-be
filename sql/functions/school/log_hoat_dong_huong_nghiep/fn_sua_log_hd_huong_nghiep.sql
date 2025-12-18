DROP FUNCTION IF EXISTS school.fn_sua_log_hd_huong_nghiep;

CREATE OR REPLACE FUNCTION school.fn_sua_log_hd_huong_nghiep(
    p_user_id bigint,
    p_hd_id bigint,
    p_nn_quan_tam varchar(500),
    p_muc_do_hieu_biet int,
    p_ky_nang_han_che varchar(500),
    p_cai_thien varchar(500)
)
    RETURNS SETOF school.v_log_hoat_dong_huong_nghiep
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_lop_id bigint;
BEGIN
    -- 1️⃣ Kiểm tra người dùng tồn tại
    IF EXISTS (SELECT 1
               FROM school.hoat_dong_huong_nghiep
               WHERE id = p_hd_id
                 AND thoi_gian_ket_thuc <= now()) THEN
        RAISE EXCEPTION 'Hoạt động hướng nghiệp với id % đã hết hạn', p_hd_id;
    END IF;
    if exists(select 1
              from school.hoat_dong_huong_nghiep
              where id = p_hd_id
                and thoi_gian_bat_dau >= now()) then
        raise exception 'Hoạt động hướng nghiệp với id % chưa tới hạn', p_hd_id;
    end if;

    SELECT hs.lop_id
    INTO v_lop_id
    FROM auth.hoc_sinh hs
    WHERE hs.user_id = p_user_id;

    IF NOT EXISTS(SELECT 1
                  FROM school.lop_hoat_dong_huong_nghiep lhd
                  WHERE lhd.hoat_dong_id = p_hd_id
                    AND lhd.lop_id = v_lop_id) THEN
        RAISE EXCEPTION 'Học sinh với id % không thuộc lớp được phân cho hoạt động', p_user_id;
    END IF;

    -- 3️⃣ Cập nhật log (không thay đổi user_id và hd_id)
    UPDATE school.hs_hoat_dong_huong_nghiep
    SET nn_quan_tam      = p_nn_quan_tam,
        muc_do_hieu_biet = p_muc_do_hieu_biet,
        ky_nang_han_che  = p_ky_nang_han_che,
        cai_thien        = p_cai_thien
    WHERE user_id = p_user_id
      AND hd_id = p_hd_id;

    -- 4️⃣ Trả về record vừa sửa
    RETURN QUERY
        SELECT *
        FROM school.v_log_hoat_dong_huong_nghiep
        WHERE id_hoat_dong = p_hd_id
          AND id_hoc_sinh = p_user_id
        LIMIT 1;
END;
$$;
