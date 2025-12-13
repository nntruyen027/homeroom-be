CREATE TABLE dm_chung.xa (
    id BIGSERIAL PRIMARY KEY,
    ten VARCHAR(120) NOT NULL,
    ghi_chu TEXT,

    tinh_id BIGINT,

    CONSTRAINT fk_tinh FOREIGN KEY (tinh_id) REFERENCES dm_chung.tinh(id) ON DELETE CASCADE
);
