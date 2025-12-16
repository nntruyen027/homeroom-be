DROP FUNCTION IF EXISTS school.fn_dem_tat_ca_hoat_dong_huong_nghiep;

CREATE OR REPLACE FUNCTION school.fn_dem_tat_ca_hoat_dong_huong_nghiep(
    p_nguoi_tao_id BIGINT,
    p_search VARCHAR(500)
)
    RETURNS BIGINT
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_total BIGINT;
BEGIN
    SELECT COUNT(*)
    INTO v_total
    FROM school.v_hoat_dong_huong_nghiep
    WHERE nguoi_tao_id = p_nguoi_tao_id
      AND (
        p_search IS NULL
            OR p_search = ''
            OR unaccent(lower(ten_hoat_dong))
            LIKE '%' || unaccent(lower(p_search)) || '%'
        );

    RETURN v_total;
END;
$$;
