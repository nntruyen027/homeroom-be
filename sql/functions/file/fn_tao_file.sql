DROP FUNCTION IF EXISTS fn_tao_file;

CREATE OR REPLACE FUNCTION fn_tao_file(
    p_file_name TEXT,
    p_stored_name TEXT,
    p_url TEXT,
    p_content_type VARCHAR(255),
    p_size BIGINT,
    p_user_id BIGINT
)
    RETURNS SETOF v_file
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_file_id BIGINT;
BEGIN
    -- insert file
    INSERT INTO files (file_name,
                       stored_name,
                       url,
                       content_type,
                       size,
                       user_id,
                       uploaded_at)
    VALUES (p_file_name,
            p_stored_name,
            p_url,
            p_content_type,
            p_size,
            p_user_id,
            now())
    RETURNING id INTO v_file_id;

    -- trả về file vừa tạo
    RETURN QUERY
        SELECT *
        FROM v_file
        WHERE id = v_file_id;
END;
$$;
