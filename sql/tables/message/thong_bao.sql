drop table if exists message.thong_bao;

create table message.thong_bao
(
    id            bigint primary key generated always as identity,
    tieu_de       varchar(500) not null,
    noi_dung      text         not null,
    lop_id        bigint       not null references school.lop (id) on delete cascade,
    nguoi_tao_id  bigint       not null references auth.giao_vien (user_id) on delete cascade,
    thoi_gian_tao timestamp default now()
);
