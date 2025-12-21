drop function if exists auth.fn_is_user_online;

CREATE OR REPLACE FUNCTION auth.fn_is_user_online(
    p_user_id BIGINT
)
    RETURNS BOOLEAN
    LANGUAGE sql
AS
$$
SELECT COALESCE(is_online, FALSE)
FROM auth.user_presence
WHERE user_id = p_user_id;
$$;
