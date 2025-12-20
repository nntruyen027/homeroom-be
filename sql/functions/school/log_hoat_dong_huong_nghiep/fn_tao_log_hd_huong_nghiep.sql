drop function if exists school.fn_tao_log_hd_huong_nghiep;

create or replace function school.fn_tao_log_hd_huong_nghiep(
    p_user_id bigint,
    p_hd_id bigint,
    p_nn_quan_tam varchar(500),
    p_muc_do_hieu_biet int,
    p_ky_nang_han_che varchar(500),
    p_cai_thien varchar(500)
)
    returns setof school.v_log_hoat_dong_huong_nghiep
    language plpgsql
as
$$
DECLARE
    v_lop_id bigint;
BEGIN
    -- check user
    IF NOT EXISTS (SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'Người dùng với id % không tồn tại', p_user_id;
    END IF;

    -- check hoạt động
    IF NOT EXISTS (SELECT 1 FROM school.hoat_dong_huong_nghiep WHERE id = p_hd_id) THEN
        RAISE EXCEPTION 'Hoạt động hướng nghiệp với id % không tồn tại', p_hd_id;
    END IF;

    -- check thời gian
    IF EXISTS (SELECT 1
               FROM school.hoat_dong_huong_nghiep
               WHERE id = p_hd_id
                 AND thoi_gian_ket_thuc <= now()) THEN
        RAISE EXCEPTION 'Hoạt động hướng nghiệp với id % đã hết hạn', p_hd_id;
    END IF;

    IF EXISTS (SELECT 1
               FROM school.hoat_dong_huong_nghiep
               WHERE id = p_hd_id
                 AND thoi_gian_bat_dau > now()) THEN
        RAISE EXCEPTION 'Hoạt động hướng nghiệp với id % chưa tới hạn', p_hd_id;
    END IF;

    -- lấy lớp học sinh
    SELECT hs.lop_id
    INTO v_lop_id
    FROM auth.hoc_sinh hs
    WHERE hs.user_id = p_user_id;

    -- check phân lớp
    IF NOT EXISTS (SELECT 1
                   FROM school.lop_hoat_dong_huong_nghiep lhd
                   WHERE lhd.hoat_dong_id = p_hd_id
                     AND lhd.lop_id = v_lop_id) THEN
        RAISE EXCEPTION 'Học sinh với id % không thuộc lớp được phân cho hoạt động', p_user_id;
    END IF;

    -- ================= UPSERT LOG =================
    IF EXISTS (SELECT 1
               FROM school.hs_hoat_dong_huong_nghiep
               WHERE user_id = p_user_id
                 AND hd_id = p_hd_id) THEN
        -- UPDATE
        UPDATE school.hs_hoat_dong_huong_nghiep
        SET nn_quan_tam      = p_nn_quan_tam,
            muc_do_hieu_biet = p_muc_do_hieu_biet,
            ky_nang_han_che  = p_ky_nang_han_che,
            cai_thien        = p_cai_thien
        WHERE user_id = p_user_id
          AND hd_id = p_hd_id;
    ELSE
        -- INSERT
        INSERT INTO school.hs_hoat_dong_huong_nghiep(user_id,
                                                     hd_id,
                                                     nn_quan_tam,
                                                     muc_do_hieu_biet,
                                                     ky_nang_han_che,
                                                     cai_thien)
        VALUES (p_user_id,
                p_hd_id,
                p_nn_quan_tam,
                p_muc_do_hieu_biet,
                p_ky_nang_han_che,
                p_cai_thien);
    END IF;

    -- trả về log vừa tạo / cập nhật
    RETURN QUERY
        SELECT *
        FROM school.v_log_hoat_dong_huong_nghiep
        WHERE id_hoat_dong = p_hd_id
          AND id_hoc_sinh = p_user_id
        LIMIT 1;

END;
$$;
