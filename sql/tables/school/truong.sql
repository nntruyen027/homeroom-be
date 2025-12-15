CREATE TABLE school.truong (
    id BIGSERIAL PRIMARY KEY,
    ten VARCHAR(120) NOT NULL,
    xa_id BIGINT,
    dia_chi_chi_tiet VARCHAR(500),
    hinh_anh TEXT,
    logo TEXT,

    CONSTRAINT fk_xa FOREIGN KEY (xa_id) REFERENCES dm_chung.xa(id) ON DELETE SET NULL
);
