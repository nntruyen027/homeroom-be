CREATE TABLE school.lop (
    id BIGSERIAL PRIMARY KEY,
    ten VARCHAR(120) NOT NULL,
    hinh_anh TEXT,

    truong_id BIGINT,
    giao_vien_id BIGINT,

    CONSTRAINT fk_truong FOREIGN KEY (truong_id) REFERENCES school.truong(id) ON DELETE SET NULL,
    CONSTRAINT fk_giao_vien FOREIGN KEY (giao_vien_id) REFERENCES auth.users(id) ON DELETE SET NULL
);
