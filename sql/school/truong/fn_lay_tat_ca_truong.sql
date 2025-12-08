DROP FUNCTION IF EXISTS school.fn_lay_tat_ca_truong;

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
	xa_id BIGINT,
	ten_xa VARCHAR(120),
	tinh_id BIGINT,
	ten_tinh VARCHAR(120),
    dia_chi_chi_tiet VARCHAR(500),
    hinh_anh TEXT,
    logo TEXT
)
AS $$
BEGIN
    RETURN QUERY
    SELECT t.id,
           t.ten,
		   t.xa_id,
		   x.ten AS ten_xa,
		   ti.id AS tinh_id,
		   ti.ten AS ten_tinh,
           t.dia_chi_chi_tiet,
           t.hinh_anh,
           t.logo
    FROM school.truong t
	LEFT JOIN dm_chung.xa x ON x.id = t.xa_id
	LEFT JOIN dm_chung.tinh ti ON ti.id = x.tinh_id
    WHERE
        p_search IS NULL OR p_search = ''
        OR unaccent(lower(t.ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
    ORDER BY t.ten
    OFFSET p_offset
    LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;
