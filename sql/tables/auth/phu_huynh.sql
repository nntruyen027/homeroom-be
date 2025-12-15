DROP TABLE IF EXISTS auth.phu_huynh;

CREATE TABLE auth.phu_huynh
(
    user_id        BIGINT PRIMARY KEY,
    hoc_sinh_id    BIGINT NOT NULL,
    nghe_nghiep_id BIGINT,

    loai_phu_huynh VARCHAR(50),
    so_dien_thoai  VARCHAR(20),

    CONSTRAINT fk_phu_huynh_user
        FOREIGN KEY (user_id)
            REFERENCES auth.users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_phu_huynh_hoc_sinh
        FOREIGN KEY (hoc_sinh_id)
            REFERENCES auth.hoc_sinh (user_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_phu_huynh_nghe_nghiep
        FOREIGN KEY (nghe_nghiep_id)
            REFERENCES dm_chung.nghe_nghiep (id)
            ON DELETE CASCADE
);
