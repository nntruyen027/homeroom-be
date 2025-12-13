DROP FUNCTION IF EXISTS dm_chung.fn_sua_tinh;

CREATE OR REPLACE FUNCTION dm_chung.fn_sua_tinh(
    p_id BIGINT,
    p_ten VARCHAR(120),
    p_ghi_chu TEXT
)
    RETURNS SETOF dm_chung.v_tinh
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM dm_chung.v_tinh t WHERE t.out_id = p_id) THEN
        RAISE EXCEPTION USING MESSAGE = format('Tỉnh với id %s không tồn tại', p_id);
    END IF;

    UPDATE dm_chung.tinh
    SET ten     = p_ten,
        ghi_chu = p_ghi_chu
    WHERE id = p_id;

    RETURN QUERY
        SELECT *
        FROM dm_chung.v_tinh t
        WHERE t.out_id = p_id;
END;
$$ LANGUAGE plpgsql;
