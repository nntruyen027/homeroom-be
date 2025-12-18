DROP FUNCTION IF EXISTS school.fn_phan_hoat_dong_cho_lop;

CREATE OR REPLACE FUNCTION school.fn_phan_hoat_dong_cho_lop(
    p_hoat_dong_id BIGINT,
    p_ds_lop_id BIGINT[]
)
    RETURNS SETOF school.v_hoat_dong_huong_nghiep
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- 1️⃣ Kiểm tra hoạt động tồn tại
    IF NOT EXISTS (SELECT 1
                   FROM school.hoat_dong_huong_nghiep
                   WHERE id = p_hoat_dong_id) THEN
        RAISE EXCEPTION 'Hoạt động với id % không tồn tại', p_hoat_dong_id;
    END IF;

    -- 2️⃣ Xoá phân công cũ (nếu cập nhật lại)
    DELETE
    FROM school.lop_hoat_dong_huong_nghiep
    WHERE hoat_dong_id = p_hoat_dong_id;

    -- 3️⃣ Thêm phân công mới
    INSERT INTO school.lop_hoat_dong_huong_nghiep (hoat_dong_id, lop_id)
    SELECT p_hoat_dong_id,
           lop_id
    FROM unnest(p_ds_lop_id) AS lop_id;

    -- 4️⃣ Trả về view hoạt động
    RETURN QUERY
        SELECT *
        FROM school.v_hoat_dong_huong_nghiep
        WHERE hoat_dong_id = p_hoat_dong_id;
END;
$$;
