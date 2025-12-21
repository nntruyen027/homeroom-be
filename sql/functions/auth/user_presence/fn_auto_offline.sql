drop function if exists auth.fn_auto_offline;

CREATE OR REPLACE FUNCTION auth.fn_auto_offline(
    p_timeout_minutes INT
)
    RETURNS INT
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count INT;
BEGIN
    UPDATE auth.user_presence
    SET is_online  = FALSE,
        updated_at = now()
    WHERE is_online = TRUE
      AND last_seen < now() - make_interval(mins => p_timeout_minutes);

    GET DIAGNOSTICS v_count = ROW_COUNT;
    RETURN v_count;
END;
$$;
