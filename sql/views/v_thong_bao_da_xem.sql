create or replace view message.v_thong_bao_da_xem as
select tb.id as thong_bao_id,
       tb.tieu_de,
       tb.noi_dung,
       tb.lop_id,
       tb.thoi_gian_tao,

       -- danh sách user đã xem
       coalesce(
                       jsonb_agg(
                       distinct jsonb_build_object(
                               'id', u.id,
                               'ten', u.ho_ten,
                               'avatar', u.avatar
                                )
                                ) filter (where tbn.da_xem = true),
                       '[]'::jsonb
       )     as ds_user_da_xem
        ,
       tb.nguoi_tao_id

from message.thong_bao tb
         left join message.thong_bao_hoc_sinh tbn
                   on tb.id = tbn.thong_bao_id
         left join auth.users u
                   on tbn.user_id = u.id

group by tb.id,
         tb.tieu_de,
         tb.noi_dung,
         tb.lop_id,
         tb.thoi_gian_tao;
