DROP TABLE IF EXISTS auth.giao_vien;

CREATE TABLE auth.giao_vien
(
    user_id BIGINT PRIMARY KEY,
    bo_mon  VARCHAR(500),
    chuc_vu VARCHAR(500),

    CONSTRAINT fk_giao_vien_user FOREIGN KEY (user_id)
        REFERENCES auth.users (id)
        ON DELETE CASCADE
);


