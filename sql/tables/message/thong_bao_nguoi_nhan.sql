drop table if exists message.thong_bao_hoc_sinh;

create table message.thong_bao_hoc_sinh
(
    id            bigint primary key generated always as identity,
    thong_bao_id  bigint not null references message.thong_bao (id) on delete cascade,
    user_id       bigint not null references auth.hoc_sinh (user_id) on delete cascade,
    da_xem        boolean   default false,
    thoi_gian_xem timestamp default now(),
    unique (thong_bao_id, user_id)
);
