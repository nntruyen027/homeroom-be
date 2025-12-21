DROP FUNCTION IF EXISTS auth.fn_tim_lop_id_tu_session;

CREATE OR REPLACE FUNCTION auth.fn_tim_lop_id_tu_session(
    p_session_id VARCHAR
)
    RETURNS BIGINT
    LANGUAGE sql
AS
$$
SELECT hs.lop_id
FROM auth.user_presence up
         JOIN auth.hoc_sinh hs
              ON hs.user_id = up.user_id
WHERE up.session_id = p_session_id
LIMIT 1;
$$;
