DROP FUNCTION IF EXISTS auth.fn_kiem_tra_ton_tai_permission_theo_code;

CREATE OR REPLACE FUNCTION auth.fn_kiem_tra_ton_tai_permission_theo_code(
    p_code VARCHAR(100)
)
    RETURNS BOOLEAN
AS
$$
BEGIN
    IF EXISTS(SELECT 1 FROM auth.permissions WHERE code = p_code) THEN
        RETURN TRUE;
    end if;
    RETURN FALSE;
end;

$$ LANGUAGE plpgsql
