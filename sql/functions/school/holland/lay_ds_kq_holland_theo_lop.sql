DROP FUNCTION IF EXISTS school.lay_ds_kq_holland_theo_lop;

CREATE FUNCTION school.lay_ds_kq_holland_theo_lop(
    p_lop_id bigint
)
    RETURNS SETOF school.v_ket_qua_holland
AS
$$
BEGIN
    RETURN QUERY
        SELECT *
        FROM school.v_ket_qua_holland
        WHERE lop_id = p_lop_id
        ORDER BY split_part(
                         ho_ten,
                         ' ',
                         array_length(string_to_array(ho_ten, ' '), 1)
                 ),
                 ho_ten;
END;
$$
    LANGUAGE plpgsql;


