DROP FUNCTION IF EXISTS school.fn_tao_truong;

CREATE OR REPLACE FUNCTION school.fn_tao_truong(
    p_ten VARCHAR(120),
    p_xa_id BIGINT,
    p_dia_chi_chi_tiet VARCHAR(500),
    p_hinh_anh TEXT,
    p_logo TEXT
)
    RETURNS SETOF school.v_truong
AS
$$
DECLARE
    new_id BIGINT;
BEGIN
    IF NOT EXISTS (select 1 from dm_chung.xa x where x.id = p_xa_id) THEN
        raise exception 'Xã với id % không tồn tại', p_xa_id;
    END IF;

    INSERT INTO school.truong (ten,
                               dia_chi_chi_tiet,
                               xa_id,
                               hinh_anh,
                               logo)
    VALUES (p_ten,
            p_dia_chi_chi_tiet,
            p_xa_id,
            p_hinh_anh,
            p_logo)
    RETURNING id INTO new_id;

    RETURN QUERY
        SELECT *
        FROM school.v_truong t
        WHERE t.id = new_id
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
