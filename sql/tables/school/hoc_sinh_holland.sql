drop table if exists school.ket_qua_holland;

create table school.ket_qua_holland
(
    id            bigserial primary key,

    hs_id         bigint     not null references auth.hoc_sinh (user_id) on delete cascade,

    diem_r        INT        NOT NULL, -- Realistic
    diem_i        INT        NOT NULL, -- Investigative
    diem_a        INT        NOT NULL, -- Artistic
    diem_s        INT        NOT NULL, -- Social
    diem_e        INT        NOT NULL, -- Enterprising
    diem_c        INT        NOT NULL, -- Conventional

    ma_holland    VARCHAR(3) NOT NULL, -- VD: RIA, SEC

    thoi_gian_lam INT,                 -- số phút làm bài
    ngay_danh_gia TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)