CREATE OR REPLACE FUNCTION school.fn_tao_truong
(
    p_ten VARCHAR(120),
    p_dia_chi TEXT,
    p_hinh_anh TEXT,
    p_logo TEXT
)
RETURNS TABLE
(
    out_id BIGINT,
    ten VARCHAR(120),
    dia_chi TEXT,
    hinh_anh TEXT,
    logo VARCHAR(50)
)
AS $$
DECLARE
    new_id BIGINT;
BEGIN
    INSERT INTO school.truong (
        ten,
        dia_chi,
        hinh_anh,
        logo
    )
    VALUES (
        p_ten,
        p_dia_chi,
        p_hinh_anh,
        p_logo
    )
    RETURNING id INTO new_id;

    RETURN QUERY
    SELECT
        t.id AS out_id,
        t.ten,
        t.dia_chi,
        t.hinh_anh,
        t.logo
    FROM school.truong t
    WHERE u.id = new_id
    LIMIT 1;
END;
$$ LANGUAGE plpgsql;
