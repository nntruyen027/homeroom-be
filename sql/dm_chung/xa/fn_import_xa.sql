DROP FUNCTION IF EXISTS dm_chung.fn_import_xa;

CREATE OR REPLACE FUNCTION dm_chung.fn_import_xa(p_xas dm_chung.xa_input[])
RETURNS TEXT
AS $$
DECLARE
    item dm_chung.xa_input;
    i INT := 1;
BEGIN
    FOREACH item IN ARRAY p_xas LOOP
        BEGIN
            INSERT INTO dm_chung.xa(ten, ghi_chu, tinh_id)
            VALUES (item.ten, item.ghi_chu, item.tinh_id);

        EXCEPTION
            WHEN unique_violation THEN
                RETURN format(
                    'Lỗi tại dòng %s: Dữ liệu trùng (ten=%s, tinh_id=%s)',
                    i, item.ten, item.tinh_id
                );

            WHEN foreign_key_violation THEN
                RETURN format(
                    'Lỗi tại dòng %s: tinh_id %s không tồn tại (ten=%s)',
                    i, item.tinh_id, item.ten
                );

            WHEN not_null_violation THEN
                RETURN format(
                    'Lỗi tại dòng %s: Thiếu dữ liệu bắt buộc (ten=%s, tinh_id=%s)',
                    i, item.ten, item.tinh_id
                );

            WHEN others THEN
                RETURN format(
                    'Lỗi không xác định tại dòng %s (ten=%s, tinh_id=%s): %s',
                    i, item.ten, item.tinh_id, SQLERRM
                );
        END;

        i := i + 1;
    END LOOP;

    RETURN 'OK';
END;
$$ LANGUAGE plpgsql;
