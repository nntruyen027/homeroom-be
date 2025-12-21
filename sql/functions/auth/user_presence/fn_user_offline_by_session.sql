DROP FUNCTION IF EXISTS auth.fn_user_offline_by_session;

CREATE OR REPLACE FUNCTION auth.fn_user_offline_by_session(
    p_session_id VARCHAR
)
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
BEGIN
    UPDATE auth.user_presence
    SET is_online  = FALSE,
        updated_at = now()
    WHERE session_id = p_session_id;
END;
$$;
