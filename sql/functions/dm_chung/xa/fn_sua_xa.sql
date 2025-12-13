DROP FUNCTION IF EXISTS dm_chung.fn_sua_xa;

CREATE OR REPLACE FUNCTION dm_chung.fn_sua_xa(
    p_id BIGINT,
    p_ten VARCHAR(120),
    p_ghi_chu TEXT,
    p_tinh_id BIGINT
)
    RETURNS SETOF dm_chung.v_xa
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM dm_chung.xa where id = p_id) THEN
        RAISE EXCEPTION 'Xã với id % không tồn tại', p_id;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM dm_chung.tinh where id = p_tinh_id) THEN
        RAISE EXCEPTION 'Tỉnh với id % không tồn tại', p_tinh_id;
    END IF;

    UPDATE dm_chung.xa
    SET ten     = p_ten,
        ghi_chu = p_ghi_chu,
        tinh_id = p_tinh_id
    WHERE id = p_id;

    RETURN QUERY
        SELECT *
        FROM dm_chung.v_xa x
        WHERE x.out_id = p_id
        LIMIT 1;

END;
$$ LANGUAGE plpgsql;
