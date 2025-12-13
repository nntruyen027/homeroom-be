CREATE OR REPLACE FUNCTION school.fn_xoa_truong(
    p_id BIGINT
)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
DECLARE
    v_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM school.truong
        WHERE id = p_id
    ) INTO v_exists;

    IF NOT v_exists THEN
        RETURN FALSE;
    END IF;

    DELETE FROM school.truong WHERE id = p_id;

    RETURN TRUE;
END;
$$;
