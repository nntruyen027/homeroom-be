DROP VIEW IF EXISTS school.v_hoat_dong_huong_nghiep cascade;

CREATE VIEW school.v_hoat_dong_huong_nghiep AS
SELECT hd.id      AS hoat_dong_id,
       hd.ten     AS ten_hoat_dong,
       hd.thoi_gian_bat_dau,
       hd.thoi_gian_ket_thuc,
       hd.ghi_chu,

       hd.nguoi_tao_id,
       u.username AS nguoi_tao_username,
       u.ho_ten   AS nguoi_tao_ho_ten
FROM school.hoat_dong_huong_nghiep hd
         LEFT JOIN auth.users u
                   ON u.id = hd.nguoi_tao_id;


select *
from school.fn_lay_tat_ca_hoat_dong_huong_nghiep
     (2, '', 0, 10);