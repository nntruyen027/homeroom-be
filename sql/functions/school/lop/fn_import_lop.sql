CREATE OR REPLACE FUNCTION school.fn_import_lop(p_lops school.lop_input[])
    RETURNS void AS
$$
BEGIN
    INSERT INTO school.lop(ten, hinh_anh, truong_id, giao_vien_id)
    SELECT l.ten, l.hinh_anh, l.truong_id, l.giao_vien_id
    FROM unnest(p_lops) l;
END;
$$ LANGUAGE plpgsql;