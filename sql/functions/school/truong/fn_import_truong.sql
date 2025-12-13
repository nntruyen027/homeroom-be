DROP FUNCTION IF EXISTS school.fn_import_truong;

CREATE OR REPLACE FUNCTION school.fn_import_truong(p_truongs school.truong_input[])
RETURNS void AS $$
BEGIN
    INSERT INTO school.truong(ten, dia_chi_chi_tiet, xa_id, hinh_anh, logo)
    SELECT t.ten, t.dia_chi_chi_tiet, t.xa_id, t.hinh_anh, t.logo
    FROM unnest(p_truongs) t;
END;
$$ LANGUAGE plpgsql;
