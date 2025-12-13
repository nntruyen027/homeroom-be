CREATE OR REPLACE FUNCTION dm_chung.fn_import_tinh(p_tinhs dm_chung.tinh_input[])
RETURNS void AS $$
BEGIN
    INSERT INTO dm_chung.tinh(ten, ghi_chu)
    SELECT t.ten, t.ghi_chu
    FROM unnest(p_tinhs) t;
END;
$$ LANGUAGE plpgsql;
