CREATE OR REPLACE FUNCTION school.fn_lay_tat_ca_truong
(
    p_search VARCHAR(500),
    p_offset INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
RETURNS TABLE
(
    out_id BIGINT,
    ten VARCHAR(120),
    dia_chi TEXT,
    hinh_anh TEXT,
    logo TEXT
)
AS $$
BEGIN
    RETURN QUERY
    SELECT t.id,
           t.ten,
           t.dia_chi,
           t.hinh_anh,
           t.logo
    FROM school.truong t
    WHERE
        p_search IS NULL OR p_search = ''
        OR unaccent(lower(t.ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
    ORDER BY t.ten
    OFFSET (p_offset - 1) * p_limit
    LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
