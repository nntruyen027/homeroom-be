DROP FUNCTION IF EXISTS fn_xoa_file;

CREATE OR REPLACE FUNCTION fn_xoa_file(
    p_file_id BIGINT
)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
BEGIN
    DELETE
    FROM files
    WHERE id = p_file_id;

    RETURN FOUND;
END;
$$;
