DROP FUNCTION IF EXISTS auth.fn_import_hoc_sinh;

CREATE OR REPLACE FUNCTION auth.fn_import_hoc_sinh(
    p_hoc_sinhs auth.hoc_sinh_input[]
)
    RETURNS SETOF auth.v_hoc_sinh_full
    LANGUAGE plpgsql
AS
$$
DECLARE
    hs          auth.hoc_sinh_input;
    v_ngay_sinh DATE;
    v_la_nam    BOOLEAN;
BEGIN
    FOREACH hs IN ARRAY p_hoc_sinhs
        LOOP
            /* =========================
               CONVERT NGÀY SINH
               ========================= */
            IF hs.ngay_sinh IS NULL OR btrim(hs.ngay_sinh) = '' THEN
                v_ngay_sinh := NULL;
            ELSE
                BEGIN
                    -- dd/MM/yyyy
                    v_ngay_sinh := to_date(hs.ngay_sinh, 'DD/MM/YYYY');
                EXCEPTION
                    WHEN others THEN
                        BEGIN
                            -- yyyy-MM-dd
                            v_ngay_sinh := to_date(hs.ngay_sinh, 'YYYY-MM-DD');
                        EXCEPTION
                            WHEN others THEN
                                RAISE EXCEPTION
                                    'Ngày sinh không hợp lệ: "%" (username=%)',
                                    hs.ngay_sinh, hs.username;
                        END;
                END;
            END IF;

            /* =========================
               CONVERT GIỚI TÍNH
               ========================= */
            IF hs.la_nam IS NULL OR btrim(hs.la_nam) = '' THEN
                v_la_nam := NULL;
            ELSE
                CASE lower(btrim(hs.la_nam))
                    WHEN 'nam' THEN v_la_nam := TRUE;
                    WHEN 'true' THEN v_la_nam := TRUE;
                    WHEN '1' THEN v_la_nam := TRUE;
                    WHEN 'x' THEN v_la_nam := TRUE;

                    WHEN 'nu' THEN v_la_nam := FALSE;
                    WHEN 'nữ' THEN v_la_nam := FALSE;
                    WHEN 'false' THEN v_la_nam := FALSE;
                    WHEN '0' THEN v_la_nam := FALSE;
                    ELSE RAISE EXCEPTION
                        'Giới tính không hợp lệ: "%" (username=%)',
                        hs.la_nam, hs.username;
                    END CASE;
            END IF;

            /* =========================
               GỌI FUNCTION TẠO HỌC SINH
               ========================= */
            RETURN QUERY
                SELECT *
                FROM auth.fn_tao_hoc_sinh(
                        hs.username,
                        hs.password,
                        hs.ho_ten,
                        hs.lop_id,
                        v_ngay_sinh,
                        v_la_nam,
                        NULL,
                        ''
                     );
        END LOOP;
END;
$$;
