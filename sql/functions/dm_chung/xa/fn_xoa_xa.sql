CREATE OR REPLACE FUNCTION dm_chung.fn_xoa_xa(
    p_id BIGINT
)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
DECLARE
    v_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM dm_chung.xa
        WHERE id = p_id
    ) INTO v_exists;

    IF NOT v_exists THEN
        RETURN FALSE;
    END IF;

    DELETE FROM dm_chung.xa WHERE id = p_id;

    RETURN TRUE;
END;
$$;
