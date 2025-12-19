DROP FUNCTION IF EXISTS school.fn_lay_log_hd_huong_nghiep;

CREATE OR REPLACE FUNCTION school.fn_lay_log_hd_huong_nghiep(
    p_user_id bigint,
    p_hd_id bigint,
    p_offset int default 0,
    p_limit int default 10
)
    RETURNS SETOF school.v_log_hoat_dong_huong_nghiep
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- Kiểm tra người dùng nếu có truyền
    IF p_user_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'Người dùng với id % không tồn tại', p_user_id;
    END IF;

    -- Kiểm tra hoạt động
    IF p_hd_id is not null and NOT EXISTS(SELECT 1 FROM school.hoat_dong_huong_nghiep WHERE id = p_hd_id) THEN
        RAISE EXCEPTION 'Hoạt động hướng nghiệp với id % không tồn tại', p_hd_id;
    END IF;

    -- Trả về log
    RETURN QUERY
        SELECT *
        FROM school.v_log_hoat_dong_huong_nghiep
        WHERE (p_user_id IS NULL OR id_hoc_sinh = p_user_id)
          AND (p_hd_id is null or id_hoat_dong = p_hd_id)
        OFFSET p_offset LIMIT p_limit;

END;
$$;
