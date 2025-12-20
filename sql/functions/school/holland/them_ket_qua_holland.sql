DROP FUNCTION IF EXISTS school.fn_them_ket_qua_holland;

CREATE FUNCTION school.fn_them_ket_qua_holland(
    p_user_id bigint,
    p_diem_r int,
    p_diem_i int,
    p_diem_a int,
    p_diem_s int,
    p_diem_e int,
    p_diem_c int,
    p_thoi_gian_lam int -- tính bằng giây
)
    RETURNS SETOF school.v_ket_qua_holland
AS
$$
DECLARE
    v_ma_holland varchar(3);
BEGIN
    -- Tính ma_holland: 3 chữ cái của 3 điểm cao nhất
    SELECT string_agg(c, '')
    INTO v_ma_holland
    FROM (SELECT k.c
          FROM (VALUES ('R', p_diem_r),
                       ('I', p_diem_i),
                       ('A', p_diem_a),
                       ('S', p_diem_s),
                       ('E', p_diem_e),
                       ('C', p_diem_c)) AS k(c, diem)
          ORDER BY diem DESC
          LIMIT 3) t;

    -- Chèn vào bảng ket_qua_holland
    INSERT INTO school.ket_qua_holland(hs_id,
                                       diem_r, diem_i, diem_a, diem_s, diem_e, diem_c,
                                       ma_holland,
                                       thoi_gian_lam,
                                       ngay_danh_gia)
    VALUES (p_user_id,
            p_diem_r, p_diem_i, p_diem_a, p_diem_s, p_diem_e, p_diem_c,
            v_ma_holland,
            p_thoi_gian_lam,
            CURRENT_TIMESTAMP);

    -- Trả về bản ghi vừa thêm
    RETURN QUERY
        SELECT *
        FROM school.v_ket_qua_holland
        WHERE hs_id = p_user_id
        ORDER BY ngay_danh_gia DESC
        LIMIT 1;
END;
$$
    LANGUAGE plpgsql;
