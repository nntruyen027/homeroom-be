drop function if exists auth.fn_get_user_presence;

CREATE OR REPLACE FUNCTION auth.fn_get_user_presence(
    p_user_id BIGINT
)
    RETURNS TABLE
            (
                user_id   BIGINT,
                is_online BOOLEAN,
                last_seen TIMESTAMP
            )
    LANGUAGE sql
AS
$$
SELECT user_id,
       is_online,
       last_seen
FROM auth.user_presence
WHERE user_id = p_user_id;
$$;
