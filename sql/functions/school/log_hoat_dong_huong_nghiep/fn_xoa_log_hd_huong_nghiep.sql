DROP FUNCTION IF EXISTS school.fn_xoa_log_hd_huong_nghiep;

CREATE OR REPLACE FUNCTION school.fn_xoa_log_hd_huong_nghiep(
    p_user_id bigint,
    p_hd_id bigint
)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- 1️⃣ Kiểm tra người dùng tồn tại
    IF NOT EXISTS(SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'Người dùng với id % không tồn tại', p_user_id;
    END IF;

    -- 2️⃣ Kiểm tra hoạt động tồn tại
    IF NOT EXISTS(SELECT 1 FROM school.hoat_dong_huong_nghiep WHERE id = p_hd_id) THEN
        RAISE EXCEPTION 'Hoạt động hướng nghiệp với id % không tồn tại', p_hd_id;
    END IF;

    IF EXISTS (SELECT 1
               FROM school.hoat_dong_huong_nghiep
               WHERE id = p_hd_id
                 AND thoi_gian_ket_thuc <= now()) THEN
        RAISE EXCEPTION 'Hoạt động hướng nghiệp với id % đã hết hạn sửa', p_hd_id;
    END IF;

    delete from school.hs_hoat_dong_huong_nghiep where user_id = p_user_id and hd_id = p_hd_id;
END;
$$;
