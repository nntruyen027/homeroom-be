DROP FUNCTION IF EXISTS dm_chung.fn_tao_tinh;

CREATE OR REPLACE FUNCTION dm_chung.fn_tao_tinh(
    p_ten VARCHAR(120),
    p_ghi_chu TEXT
)
    RETURNS SETOF dm_chung.v_tinh
AS
$$
DECLARE
    new_id BIGINT;
BEGIN
    INSERT INTO dm_chung.tinh (ten,
                               ghi_chu)
    VALUES (p_ten,
            p_ghi_chu)
    RETURNING id INTO new_id;

    RETURN QUERY
        SELECT *
        FROM dm_chung.v_tinh t
        WHERE t.out_id = new_id
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
