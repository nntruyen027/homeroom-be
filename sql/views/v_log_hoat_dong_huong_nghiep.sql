drop view if exists school.v_log_hoat_dong_huong_nghiep;

create view school.v_log_hoat_dong_huong_nghiep as
select l.nn_quan_tam,
       l.ky_nang_han_che,
       l.muc_do_hieu_biet,
       l.cai_thien,
       l.nhan_xet,
       l.user_id as id_hoc_sinh,
       l.hd_id   as id_hoat_dong,
       l.gv_id,
       hdhn.ten  as ten_hoat_dong,
       u2.ho_ten as ten_hoc_sinh,
       u3.ho_ten as ten_giao_vien
from school.hs_hoat_dong_huong_nghiep l
         left join school.hoat_dong_huong_nghiep hdhn on l.hd_id = hdhn.id
         left join auth.hoc_sinh u on l.user_id = u.user_id
         left join auth.giao_vien gv on l.gv_id = gv.user_id
         left join auth.users u2 on u.user_id = u2.id
         left join auth.users u3 on gv.user_id = u3.id;
