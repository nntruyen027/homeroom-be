drop function if exists auth.fn_ds_hoc_sinh_online;

CREATE OR REPLACE FUNCTION auth.fn_ds_hoc_sinh_online(
    p_lop_id BIGINT
)
    RETURNS TABLE
            (
                user_id   BIGINT,
                last_seen TIMESTAMP
            )
    LANGUAGE sql
AS
$$
SELECT hs.user_id,
       up.last_seen
FROM auth.hoc_sinh hs
         JOIN auth.user_presence up
              ON up.user_id = hs.user_id
WHERE hs.lop_id = p_lop_id
  AND up.is_online = TRUE;
$$;
