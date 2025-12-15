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

--     realistic_score       INT,
--     investigative_score   INT,
--     artistic_score        INT,
--     social_score          INT,
--     enterprising_score    INT,
--     conventional_score    INT,
--     assessment_result     VARCHAR(500),

    CONSTRAINT fk_hoc_sinh_user FOREIGN KEY (user_id)
        REFERENCES auth.users (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_hoc_sinh_lop FOREIGN KEY (lop_id)
        REFERENCES school.lop (id)
        ON DELETE SET NULL
);
