DROP TABLE IF EXISTS auth.thong_tin_nguoi_dung;

CREATE TABLE auth.thong_tin_nguoi_dung
(
    user_id          BIGINT PRIMARY KEY,

    xa_id            BIGINT,
    dia_chi_chi_tiet VARCHAR(500),

    ngay_sinh        DATE,
    la_nam           BOOLEAN,

    CONSTRAINT fk_profile_user FOREIGN KEY (user_id)
        REFERENCES auth.users (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_profile_xa FOREIGN KEY (xa_id)
        REFERENCES dm_chung.xa (id)
        ON DELETE SET NULL
);
