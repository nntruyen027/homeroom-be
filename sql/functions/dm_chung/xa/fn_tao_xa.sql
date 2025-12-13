DROP FUNCTION IF EXISTS dm_chung.fn_tao_xa;

CREATE OR REPLACE FUNCTION dm_chung.fn_tao_xa(
    p_ten VARCHAR(120),
    p_ghi_chu TEXT,
    p_tinh_id BIGINT
)
    RETURNS SETOF dm_chung.v_xa
AS
$$
DECLARE
    new_id BIGINT;
BEGIN
    INSERT INTO dm_chung.xa (ten,
                             ghi_chu,
                             tinh_id)
    VALUES (p_ten,
            p_ghi_chu,
            p_tinh_id)
    RETURNING id INTO new_id;

    RETURN QUERY
        SELECT *
        FROM dm_chung.v_xa x
        WHERE x.out_id = new_id
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
