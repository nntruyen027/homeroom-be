drop table if exists school.hs_hoat_dong_huong_nghiep;

create table school.hs_hoat_dong_huong_nghiep
(
    user_id          bigint references auth.hoc_sinh (user_id) on delete cascade,
    hd_id            bigint references school.hoat_dong_huong_nghiep on delete cascade,
    nn_quan_tam      varchar(500),
    muc_do_hieu_biet int,
    ky_nang_han_che  varchar(500),
    cai_thien        varchar(500),
    nhan_xet         varchar(500),
    gv_id            bigint references auth.giao_vien (user_id) on delete cascade,

    primary key (user_id, hd_id)
)