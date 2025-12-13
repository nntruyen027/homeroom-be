DROP FUNCTION IF EXISTS school.fn_xoa_hoc_sinh;

CREATE OR REPLACE FUNCTION school.fn_xoa_hoc_sinh(
    p_id BIGINT
)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_exists BOOLEAN;
BEGIN
    SELECT EXISTS (SELECT 1
                   FROM auth.users u
                            JOIN auth.user_roles ur ON ur.user_id = u.id
                            JOIN auth.roles r ON r.id = ur.role_id
                   WHERE u.id = p_id
                     AND r.code = 'STUDENT')
    INTO v_exists;

    IF NOT v_exists THEN
        RETURN FALSE;
    END IF;

    -- Xoá role trước (tránh FK)
    DELETE FROM auth.user_roles WHERE user_id = p_id;

    -- Xoá user
    DELETE FROM auth.users WHERE id = p_id;

    RETURN TRUE;
END;
$$;
