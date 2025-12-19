DROP FUNCTION IF EXISTS school.fn_dem_hdhn_theo_hoc_sinh;

CREATE OR REPLACE FUNCTION school.fn_dem_hdhn_theo_hoc_sinh(
    p_hoc_sinh_id BIGINT,
    p_search VARCHAR(500) DEFAULT NULL
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
    FROM school.v_hoat_dong_huong_nghiep v
             JOIN auth.hoc_sinh hs
                  ON hs.user_id = p_hoc_sinh_id
                      AND hs.lop_id = ANY (v.ds_lop_id)
    WHERE p_search IS NULL
       OR p_search = ''
       OR (
        v.ten_hoat_dong ILIKE '%' || p_search || '%'
            OR v.ghi_chu ILIKE '%' || p_search || '%'
        );

    RETURN v_total;
END;
$$;
