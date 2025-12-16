DROP FUNCTION IF EXISTS school.fn_phan_hoat_dong_cho_lop;

CREATE OR REPLACE FUNCTION school.fn_phan_hoat_dong_cho_lop(
    p_hoat_dong_id BIGINT,
    p_lop_ids BIGINT[]
)
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_lop_id BIGINT;
BEGIN
    -- Xoá phân công cũ
    DELETE
    FROM school.lop_hoat_dong_huong_nghiep
    WHERE hoat_dong_id = p_hoat_dong_id;

    -- Gán lại
    FOREACH v_lop_id IN ARRAY p_lop_ids
        LOOP
            INSERT INTO school.lop_hoat_dong_huong_nghiep(hoat_dong_id,
                                                          lop_id)
            VALUES (p_hoat_dong_id,
                    v_lop_id);
        END LOOP;
END;
$$;
