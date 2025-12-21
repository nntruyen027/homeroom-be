drop function if exists auth.fn_user_online;

CREATE OR REPLACE FUNCTION auth.fn_user_online(
    p_user_id BIGINT,
    p_session_id VARCHAR
)
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
BEGIN
    INSERT INTO auth.user_presence (user_id,
                                    is_online,
                                    last_seen,
                                    session_id,
                                    updated_at)
    VALUES (p_user_id,
            TRUE,
            now(),
            p_session_id,
            now())
    ON CONFLICT (user_id)
        DO UPDATE SET is_online  = TRUE,
                      last_seen  = now(),
                      session_id = p_session_id,
                      updated_at = now();
END;
$$;
