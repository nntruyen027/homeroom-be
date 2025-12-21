DROP TABLE IF EXISTS auth.hoc_sinh;

CREATE TABLE auth.hoc_sinh
(
    user_id           BIGINT PRIMARY KEY,
    lop_id            BIGINT,

    so_thich          VARCHAR(500),
    mon_hoc_yeu_thich VARCHAR(500),
    diem_manh         VARCHAR(500),
    diem_yeu          VARCHAR(500),
--     nghe_nghiep_mong_muon VARCHAR(500),
--     nhan_xet_giao_vien    VARCHAR(500,
    ghi_chu           VARCHAR(500),

    CONSTRAINT fk_hoc_sinh_user FOREIGN KEY (user_id)
        REFERENCES auth.users (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_hoc_sinh_lop FOREIGN KEY (lop_id)
        REFERENCES school.lop (id)
        ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION auth.trg_xoa_user_khi_xoa_hoc_sinh()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    DELETE
    FROM auth.users
    WHERE id = OLD.user_id;

    RETURN OLD;
END;
$$;

CREATE TRIGGER trg_after_delete_hoc_sinh
    AFTER DELETE
    ON auth.hoc_sinh
    FOR EACH ROW
EXECUTE FUNCTION auth.trg_xoa_user_khi_xoa_hoc_sinh();

