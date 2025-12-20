DROP FUNCTION IF EXISTS school.lay_ds_kq_holland_theo_hs;

CREATE FUNCTION school.lay_ds_kq_holland_theo_hs(
    p_hs_id bigint,
    p_offset int DEFAULT 0, -- thường offset bắt đầu từ 0
    p_limit int DEFAULT 10
)
    RETURNS SETOF school.v_ket_qua_holland
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM school.v_ket_qua_holland
        WHERE hs_id = p_hs_id
        ORDER BY ngay_danh_gia DESC
        OFFSET p_offset LIMIT p_limit;
END;
$$
    LANGUAGE plpgsql;
