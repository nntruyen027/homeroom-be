DROP FUNCTION IF EXISTS auth.fn_dem_tat_ca_giao_vien;

CREATE OR REPLACE FUNCTION auth.fn_dem_tat_ca_giao_vien(
    p_search VARCHAR(500)
)
    RETURNS BIGINT
    LANGUAGE plpgsql
AS
$$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT gv.user_id)
    INTO total
    FROM auth.giao_vien gv
             JOIN auth.users u ON u.id = gv.user_id
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(u.ho_ten)) LIKE '%' || unaccent(lower(p_search)) || '%';

    RETURN total;
END;
$$;



