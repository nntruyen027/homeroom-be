DROP FUNCTION IF EXISTS school.fn_dem_tat_ca_giao_vien;

CREATE OR REPLACE FUNCTION school.fn_dem_tat_ca_giao_vien(
    p_search VARCHAR(500)
)
    RETURNS BIGINT
AS
$$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT u.out_id)
    INTO total
    FROM auth.v_users_giao_vien u
    WHERE (
              p_search IS NULL OR p_search = ''
                  OR unaccent(lower(u.ho_ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
                  OR unaccent(lower(u.username)) LIKE '%' || unaccent(lower(p_search)) || '%'
              );

    RETURN total;
END;
$$ LANGUAGE plpgsql;