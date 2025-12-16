DROP TABLE IF EXISTS school.hoat_dong_huong_nghiep;

CREATE TABLE school.hoat_dong_huong_nghiep
(
    id                 BIGSERIAL NOT NULL PRIMARY KEY,
    ten                VARCHAR(120),
    thoi_gian_bat_dau  DATE,
    thoi_gian_ket_thuc DATE,
    ghi_chu            TEXT,
    nguoi_tao_id       BIGINT,

    CONSTRAINT fk_nguoi_tao FOREIGN KEY (nguoi_tao_id) REFERENCES auth.users (id) ON DELETE CASCADE
);