DROP FUNCTION IF EXISTS school.fn_dem_giao_vien_theo_truong;

CREATE OR REPLACE FUNCTION school.fn_dem_giao_vien_theo_truong(
    p_truong_id BIGINT,
    p_search VARCHAR(500)
)
    RETURNS BIGINT
AS
$$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT l.giao_vien_id)
    INTO total
    FROM school.lop l
             JOIN auth.user_roles ur ON ur.user_id = l.giao_vien_id
             JOIN auth.roles r ON r.id = ur.role_id
        AND r.code = 'TEACHER'
             JOIN auth.users u ON u.id = l.giao_vien_id
    WHERE l.truong_id = p_truong_id
      AND (
        p_search IS NULL OR p_search = ''
            OR unaccent(lower(u.ho_ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
            OR unaccent(lower(u.username)) LIKE '%' || unaccent(lower(p_search)) || '%'
        );

    RETURN total;
END;
$$ LANGUAGE plpgsql;
