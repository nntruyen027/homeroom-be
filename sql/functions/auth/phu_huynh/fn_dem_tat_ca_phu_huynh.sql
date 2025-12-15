DROP FUNCTION IF EXISTS auth.fn_dem_tat_ca_phu_huynh;

CREATE OR REPLACE FUNCTION auth.fn_dem_tat_ca_phu_huynh(
    p_search VARCHAR(500)
)
    RETURNS BIGINT
    LANGUAGE plpgsql
AS
$$
DECLARE
    total BIGINT;
BEGIN
    SELECT COUNT(DISTINCT ph.user_id)
    INTO total
    FROM auth.phu_huynh ph
             JOIN auth.users u ON u.id = ph.user_id
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(u.ho_ten)) LIKE '%' || unaccent(lower(p_search)) || '%';

    RETURN total;
END;
$$;



