CREATE OR REPLACE FUNCTION school.fn_dem_tat_ca_lop_thuoc_truong
(
    p_truong_id BIGINT,
    p_search TEXT DEFAULT NULL
)
RETURNS BIGINT
AS $$
DECLARE
    v_total BIGINT;
BEGIN
    IF NOT EXISTS(SELECT 1 FROM school.truong WHERE id = p_truong_id) THEN
        RAISE EXCEPTION 'Trường với id % không tồn tại', p_truong_id;
    END IF;

    SELECT COUNT(*)
    INTO v_total
    FROM school.lop l
    WHERE l.truong_id = p_truong_id
      AND (
            p_search IS NULL OR p_search = ''
            OR unaccent(lower(l.ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
          );

    RETURN v_total;
END;
$$ LANGUAGE plpgsql;
