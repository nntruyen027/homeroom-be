DROP FUNCTION IF EXISTS school.fn_dem_hoc_sinh_theo_lop;

CREATE OR REPLACE FUNCTION school.fn_dem_hoc_sinh_theo_lop(
    p_lop_id BIGINT,
    p_search VARCHAR(500)
)
    RETURNS BIGINT
    LANGUAGE plpgsql
AS
$$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT u.id)
    INTO total
    FROM auth.users u
             JOIN auth.user_roles ur ON ur.user_id = u.id
             JOIN auth.roles r ON r.id = ur.role_id
        AND r.code = 'STUDENT'
    WHERE u.lop_id = p_lop_id
      AND (
        p_search IS NULL OR p_search = ''
            OR unaccent(lower(u.ho_ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
            OR unaccent(lower(u.username)) LIKE '%' || unaccent(lower(p_search)) || '%'
        );

    RETURN total;
END;
$$;
