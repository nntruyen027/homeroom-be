DROP FUNCTION IF EXISTS auth.fn_dem_tat_ca_hoc_sinh;

CREATE OR REPLACE FUNCTION auth.fn_dem_tat_ca_hoc_sinh(
    p_search VARCHAR(500)
)
    RETURNS BIGINT
    LANGUAGE plpgsql
AS
$$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT hs.user_id)
    INTO total
    FROM auth.hoc_sinh hs
             JOIN auth.users u ON u.id = hs.user_id
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(u.ho_ten)) LIKE '%' || unaccent(lower(p_search)) || '%';

    RETURN total;
END;
$$;



