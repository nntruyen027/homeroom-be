DROP FUNCTION IF EXISTS school.fn_sua_truong;

CREATE OR REPLACE FUNCTION school.fn_sua_truong(
    p_id BIGINT,
    p_ten VARCHAR(120),
    p_xa_id BIGINT,
    p_dia_chi_chi_tiet VARCHAR(500),
    p_hinh_anh TEXT,
    p_logo TEXT
)
    RETURNS SETOF school.v_truong
AS
$$
BEGIN
    -- Kiểm tra tồn tại trường
    IF NOT EXISTS (SELECT 1 FROM school.truong WHERE id = p_id) THEN
        RAISE EXCEPTION 'Trường với id % không tồn tại', p_id;
    END IF;

    -- Kiểm tra xã tồn tại
    IF NOT EXISTS (SELECT 1 FROM dm_chung.xa WHERE id = p_xa_id) THEN
        RAISE EXCEPTION 'Xã với id % không tồn tại', p_xa_id;
    END IF;

    -- Update
    UPDATE school.truong
    SET ten              = p_ten,
        dia_chi_chi_tiet = p_dia_chi_chi_tiet,
        hinh_anh         = p_hinh_anh,
        logo             = p_logo,
        xa_id            = p_xa_id
    WHERE id = p_id;

    -- Trả kết quả
    RETURN QUERY
        SELECT *
        FROM school.v_truong T
        WHERE t.id = p_id
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
