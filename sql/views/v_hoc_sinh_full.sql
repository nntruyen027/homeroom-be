DROP VIEW IF EXISTS auth.v_hoc_sinh_full CASCADE;
CREATE VIEW auth.v_hoc_sinh_full AS
SELECT u.id   AS user_id,
       u.username,
       u.ho_ten,
       u.avatar,

       hs.lop_id,
       l.ten  as ten_lop,
       l.truong_id,
       tr.ten as ten_truong,

       hs.so_thich,
       hs.mon_hoc_yeu_thich,
       hs.diem_manh,
       hs.diem_yeu,
       hs.ghi_chu,

       tt.ngay_sinh,
       tt.la_nam,
       tt.xa_id,
       x.ten  as ten_xa,
       x.tinh_id,
       ti.ten as ten_tinh,
       tt.dia_chi_chi_tiet
FROM auth.hoc_sinh hs
         JOIN auth.users u ON u.id = hs.user_id
         LEFT JOIN auth.thong_tin_nguoi_dung tt ON tt.user_id = u.id
         LEFT JOIN dm_chung.xa x ON x.id = tt.xa_id
         LEFT JOIN dm_chung.tinh ti ON ti.id = x.tinh_id
         LEFT JOIN school.lop l on l.id = hs.lop_id
         LEFT JOIN school.truong tr ON tr.id = l.truong_id
;
;
