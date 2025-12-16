DROP FUNCTION IF EXISTS school.fn_xoa_hoat_dong_huong_nghiep;

CREATE OR REPLACE FUNCTION school.fn_xoa_hoat_dong_huong_nghiep(
    p_hoat_dong_id BIGINT
)
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
BEGIN
    DELETE
    FROM school.hoat_dong_huong_nghiep
    WHERE id = p_hoat_dong_id;
END;
$$;
