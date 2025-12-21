drop function if exists auth.fn_dem_hoc_sinh_online;

CREATE OR REPLACE FUNCTION auth.fn_dem_hoc_sinh_online(
    p_lop_id BIGINT
)
    RETURNS INT
    LANGUAGE sql
AS
$$
SELECT COUNT(*)
FROM auth.hoc_sinh hs
         JOIN auth.user_presence up
              ON up.user_id = hs.user_id
WHERE hs.lop_id = p_lop_id
  AND up.is_online = TRUE;
$$;
