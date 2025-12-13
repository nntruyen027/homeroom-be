DROP FUNCTION IF EXISTS auth.fn_cap_nhat_thong_tin_quan_tri_vien;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_thong_tin_quan_tri_vien(
    p_id BIGINT,
    p_avatar TEXT,
    p_ho_ten TEXT
)
    RETURNS SETOF AUTH.v_users_admin
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM auth.v_users_admin a
                   WHERE a.out_id = p_id) THEN
        RAISE EXCEPTION 'Quản trị viên với id % không tồn tại', p_id;
    END IF;

    UPDATE auth.users
    SET avatar = p_avatar,
        ho_ten = p_ho_ten
    WHERE id = p_id;

    RETURN QUERY
        SELECT *
        FROM auth.v_users_admin a
        WHERE a.out_id = p_id
        LIMIT 1;
END;
$$ LANGUAGE plpgsql;
